package com.dogworld.dogdog.purchase.interfaces.dto.response;

import com.dogworld.dogdog.member.domain.Member;
import com.dogworld.dogdog.purchase.domain.Purchase;
import com.dogworld.dogdog.purchase.domain.PurchaseItem;
import com.dogworld.dogdog.purchase.domain.PurchaseStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PurchaseFromCartResponse {
  private String shippingAddress;

  public static PurchaseFromCartResponse from(Purchase createdPurchase) {
    return PurchaseFromCartResponse.builder()
        .shippingAddress(createdPurchase.getShippingAddress())
        .build();
  }

  @Builder
  private PurchaseFromCartResponse(String shippingAddress) {
    this.shippingAddress = shippingAddress;
  }
}
