package com.dogworld.dogdog.purchase.interfaces;

import com.dogworld.dogdog.global.common.response.ApiResponse;
import com.dogworld.dogdog.purchase.application.PurchaseCommandService;
import com.dogworld.dogdog.purchase.interfaces.dto.request.PurchaseFromCartRequest;
import com.dogworld.dogdog.purchase.interfaces.dto.response.PurchaseFromCartResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.EntityResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/purchases")
public class PurchaseController {
  private final PurchaseCommandService purchaseCommandService;

  @PostMapping("/from-cart")
  public ResponseEntity<ApiResponse<PurchaseFromCartResponse>> purchaseFromCart(@Valid @RequestBody PurchaseFromCartRequest request) {
    PurchaseFromCartResponse response = purchaseCommandService.purchaseFromCart(request);
    return ResponseEntity.ok(ApiResponse.success(response));
  }

}
