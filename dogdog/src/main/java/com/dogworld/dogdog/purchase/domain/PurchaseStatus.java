package com.dogworld.dogdog.purchase.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PurchaseStatus {
  CREATED("주문 생성"),
  PENDING("결제 대기"),
  PAID("결제 완료"),
  ORDERED("주문 완료"),
  CANCELED("주문 취소")
  ;

  private final String text;
}
