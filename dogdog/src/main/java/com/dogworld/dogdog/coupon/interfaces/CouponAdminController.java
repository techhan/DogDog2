package com.dogworld.dogdog.coupon.interfaces;

import com.dogworld.dogdog.coupon.application.CouponCommandService;
import com.dogworld.dogdog.coupon.application.CouponQueryService;
import com.dogworld.dogdog.coupon.domain.repository.CouponRepository;
import com.dogworld.dogdog.coupon.interfaces.dto.response.CouponResponse;
import com.dogworld.dogdog.global.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coupons/admin/")
public class CouponAdminController {

    private final CouponCommandService couponCommandService;
    private final CouponQueryService    couponQueryService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CouponResponse>>> getAllCoupons() {
        List<CouponResponse> responses = couponQueryService.getAllCoupons();
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CouponResponse>> createCoupon(@Valid @RequestBody CouponRequest request) {
        couponCommandService.createCoupon();
    }
}
