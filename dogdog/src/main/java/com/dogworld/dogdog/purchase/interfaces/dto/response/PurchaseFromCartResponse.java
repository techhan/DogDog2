package com.dogworld.dogdog.purchase.interfaces.dto.response;

import com.dogworld.dogdog.purchase.domain.Purchase;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PurchaseFromCartResponse {
  private String shippingAddress;


  public PurchaseFromCartResponse create(Purchase purchase) {
    return PurchaseFromCartResponse.builder()
        .shippingAddress(purchase.getShippingAddress())
        .build();
  }
}
