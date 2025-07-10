package com.dogworld.dogdog.purchase.interfaces.dto.response;

import com.dogworld.dogdog.purchase.domain.PurchaseItem;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PurchaseItemResponse {

  private Long purchaseItemId;
  private Long productId;
  private String productName;
  private int quantity;
  private BigDecimal price;
  private BigDecimal totalPrice;

  @Builder
  private PurchaseItemResponse(Long purchaseItemId, Long productId, int quantity, BigDecimal price,
      BigDecimal totalPrice, String productName) {
    this.purchaseItemId = purchaseItemId;
    this.productId = productId;
    this.productName = productName;
    this.quantity = quantity;
    this.price = price;
    this.totalPrice = totalPrice;
  }

  public static PurchaseItemResponse from(PurchaseItem purchaseItem) {
    return PurchaseItemResponse.builder()
        .purchaseItemId(purchaseItem.getId())
        .productId(purchaseItem.getProduct().getId())
        .productName(purchaseItem.getProduct().getName())
        .quantity(purchaseItem.getQuantity())
        .price(purchaseItem.getPrice())
        .totalPrice(purchaseItem.getTotalPrice())
        .build();
  }
}
