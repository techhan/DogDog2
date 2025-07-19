package com.dogworld.dogdog.rating.interfaces.dto.response;

import com.dogworld.dogdog.rating.domain.Rating;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class RatingResponse {

    private Long ratingId;
    private Long productId;
    private Long memberId;
    private int score;
    private String comment;

    public static RatingResponse from(Rating rating) {
        return RatingResponse.builder()
                .ratingId(rating.getId())
                .productId(rating.getProduct().getId())
                .memberId(rating.getMember().getId())
                .score(rating.getScore())
                .comment(rating.getComment())
                .build();
    }
}
