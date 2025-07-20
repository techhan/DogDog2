package com.dogworld.dogdog.coupon.application;

import com.dogworld.dogdog.coupon.domain.Coupon;
import com.dogworld.dogdog.coupon.domain.repository.CouponRepository;
import com.dogworld.dogdog.coupon.interfaces.dto.response.CouponResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponQueryService {

    private final CouponRepository couponRepository;

    public List<CouponResponse> getAllCoupons() {
        List<Coupon> coupons = couponRepository.findAll();
        return coupons.stream()
                .map(CouponResponse::from)
                .toList();
    }
}
