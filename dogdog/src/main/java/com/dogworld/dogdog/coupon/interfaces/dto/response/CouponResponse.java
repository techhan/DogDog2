package com.dogworld.dogdog.coupon.interfaces.dto.response;

import com.dogworld.dogdog.coupon.domain.Coupon;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class CouponResponse {

    private Long couponId;

    private String name;

    private BigDecimal minOrderAmount;

    private BigDecimal maxOrderAmount;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private int usageLimit;

    private int usedCount;

    public static CouponResponse from(Coupon coupon) {
        return CouponResponse.builder()
                .couponId(coupon.getId())
                .name(coupon.getName())
                .minOrderAmount(coupon.getMinOrderAmount())
                .maxOrderAmount(coupon.getMaxDiscountAmount())
                .startDate(coupon.getStartDate())
                .endDate(coupon.getEndDate())
                .usageLimit(coupon.getUsageLimit())
                .usedCount(coupon.getUsedCount())
                .build();
    }
}
