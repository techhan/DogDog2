package com.dogworld.dogdog.coupon.application;

import com.dogworld.dogdog.coupon.domain.Coupon;
import com.dogworld.dogdog.coupon.domain.repository.CouponRepository;
import com.dogworld.dogdog.coupon.interfaces.dto.request.CouponRequest;
import com.dogworld.dogdog.coupon.interfaces.dto.response.CouponResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CouponCommandService {

    private final CouponRepository couponRepository;

    public CouponResponse createCoupon(CouponRequest request) {
        Coupon coupon = Coupon.create(request);
        return CouponResponse.from(couponRepository.saveAndFlush(coupon));
    }
}
