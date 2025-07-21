package com.dogworld.dogdog.coupon.interfaces.dto.request;

import com.dogworld.dogdog.coupon.domain.CouponType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
public class CouponRequest {

    @NotNull
    @Length(min = 2, max = 50)
    private String name;

    @NotNull
    private CouponType discountType;

    @NotNull
    @Positive
    private BigDecimal discountValue;

    private BigDecimal minOrderAmount;

    private BigDecimal maxOrderAmount;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @NotNull
    private int usageLimit;

    @NotNull
    private int issueCount;

}
