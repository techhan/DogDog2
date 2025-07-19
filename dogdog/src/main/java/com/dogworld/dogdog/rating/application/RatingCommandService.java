package com.dogworld.dogdog.rating.application;

import com.dogworld.dogdog.global.error.code.ErrorCode;
import com.dogworld.dogdog.global.error.exception.CustomException;
import com.dogworld.dogdog.member.domain.Member;
import com.dogworld.dogdog.member.domain.repository.MemberRepository;
import com.dogworld.dogdog.product.domain.Product;
import com.dogworld.dogdog.product.domain.repository.ProductRepository;
import com.dogworld.dogdog.product.infrastructure.ProductRedisRepository;
import com.dogworld.dogdog.purchase.domain.repository.PurchaseItemRepository;
import com.dogworld.dogdog.rating.domain.Rating;
import com.dogworld.dogdog.rating.domain.repository.RatingRepository;
import com.dogworld.dogdog.rating.interfaces.dto.request.RatingRequest;
import com.dogworld.dogdog.rating.interfaces.dto.response.RatingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RatingCommandService {

    private final RatingRepository ratingRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final PurchaseItemRepository purchaseItemRepository;
    private final ProductRedisRepository productRedisRepository;

    public RatingResponse createRating(RatingRequest request, Long productId) {
        Long memberId = request.getMemberId();
        Member member = getMemberBy(memberId);
        Product product = getProductBy(productId);

        validateMemberCanWriteRating(productId, memberId);

        Rating createRating = Rating.create(request, member, product);
        ratingRepository.saveAndFlush(createRating);

        updateProductRating(product);
        Product refreshed = productRepository.save(product);
        updateProductRatingZSet(refreshed);
        return RatingResponse.from(createRating);
    }

    private Member getMemberBy(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    private Product getProductBy(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
    }

    private void validateMemberCanWriteRating(Long productId, Long memberId) {
        if(!purchaseItemRepository.existsByProductIdAndPurchase_MemberId(productId, memberId)) {
            throw new CustomException(ErrorCode.RATING_PERMISSION_DENIED);
        }
    }

    private void updateProductRatingZSet(Product product) {
        productRedisRepository.addProductToRatingSortedSet(product.getId(), product.getRatingAverage());
    }

    private void updateProductRating(Product product) {
        List<Rating> ratings = ratingRepository.findAllByProductIdAndDeletedFalse(product.getId());
        int ratingCount = ratings.size();
        product.updateRatingCount(ratingCount);

        int sumScore = calculateSumScore(ratings);

        BigDecimal average = BigDecimal.ZERO;

        if(ratingCount > 0) {
            average = calculateAverageScore(sumScore, ratingCount);
        }

        product.updateRatingAverage(average);
    }

    private int calculateSumScore(List<Rating> allByProductId) {
        return allByProductId.stream()
                .mapToInt(Rating::getScore)
                .sum();
    }

    private BigDecimal calculateAverageScore(int sumScore, int ratingCount) {
        BigDecimal sumBD = BigDecimal.valueOf(sumScore);
        BigDecimal countBD = BigDecimal.valueOf(ratingCount);

        return sumBD.divide(countBD, 1, RoundingMode.HALF_UP);
    }
}
