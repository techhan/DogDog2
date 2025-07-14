package com.dogworld.dogdog.purchase.interfaces.dto.response;

import com.dogworld.dogdog.purchase.domain.Purchase;
import com.dogworld.dogdog.purchase.domain.PurchaseItem;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PurchaseFromCartResponse {

  private Long purchaseId;
  private List<PurchaseItemResponse> items;
  private BigDecimal totalPrice;
  private String shippingAddress;
  @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime orderedAt;

  public static PurchaseFromCartResponse from(Purchase purchase) {
    return PurchaseFromCartResponse.builder()
        .purchaseId(purchase.getId())
        .items(purchaseItemResponseOf(purchase.getPurchaseItems()))
        .totalPrice(purchase.getTotalPrice())
        .shippingAddress(purchase.getShippingAddress())
        .orderedAt(purchase.getOrderedAt())
        .build();
  }

  private static List<PurchaseItemResponse> purchaseItemResponseOf(List<PurchaseItem> purchaseItems) {
    return purchaseItems.stream()
        .map(PurchaseItemResponse::from)
        .toList();
  }

  @Builder
  private PurchaseFromCartResponse(Long purchaseId, List<PurchaseItemResponse> items,
      BigDecimal totalPrice, String shippingAddress, LocalDateTime orderedAt) {
    this.purchaseId = purchaseId;
    this.items = items;
    this.totalPrice = totalPrice;
    this.shippingAddress = shippingAddress;
    this.orderedAt = orderedAt;
  }

  @Override
  public String toString() {
    return "PurchaseFromCartResponse{" +
        "purchaseId=" + purchaseId +
        ", items=" + items +
        ", totalPrice=" + totalPrice +
        ", shippingAddress='" + shippingAddress + '\'' +
        ", orderedAt=" + orderedAt +
        '}';
  }
}
