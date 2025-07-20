package com.dogworld.dogdog.coupon.application;

import com.dogworld.dogdog.coupon.domain.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CouponCommandService {

    private final CouponRepository couponRepository;

}
