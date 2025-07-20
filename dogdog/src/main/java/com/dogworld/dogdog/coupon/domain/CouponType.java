package com.dogworld.dogdog.coupon.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CouponType {
    PERCENTAGE("정률 할인"),
    FIXED_AMOUNT("정액 할인")
    ;

    public final String text;
}
