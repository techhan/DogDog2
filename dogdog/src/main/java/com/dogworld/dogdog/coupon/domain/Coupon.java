package com.dogworld.dogdog.coupon.domain;

import com.dogworld.dogdog.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "coupons")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CouponType discountType;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal discountValue;

    @Column(precision = 10, scale = 2)
    private BigDecimal minOrderAmount;

    @Column(precision = 10, scale = 2)
    private BigDecimal maxDiscountAmount;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    private int usageLimit;

    @Column(nullable = false)
    private int issueCount;

    private int usedCount;

    @Builder
    private Coupon(String name, CouponType discountType, BigDecimal discountValue, BigDecimal minOrderAmount, BigDecimal maxDiscountAmount, LocalDateTime startDate, LocalDateTime endDate, int usageLimit, int issueCount) {
        this.name = name;
        this.discountType = discountType;
        this.discountValue = discountValue;
        this.minOrderAmount = minOrderAmount;
        this.maxDiscountAmount = maxDiscountAmount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.usageLimit = usageLimit;
        this.issueCount = issueCount;
    }

//    public static Coupon create() {
//        return Coupon.builder().build();
//    }
}
