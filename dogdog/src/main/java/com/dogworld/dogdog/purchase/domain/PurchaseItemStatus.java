package com.dogworld.dogdog.purchase.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PurchaseItemStatus {
  ORDERING("주문 중"),
  ORDERED("주문 완료"),
  SHIPPING("배송 중"),
  DELIVERED("배송 완료"),
  CANCELED("주문 취소"),
  RETURN_REQUEST("반품"),
  RETURNED("반품 승인 및 회수 완료"),
  REFUNDED("환불 완료")
  ;

  private final String text;
}
