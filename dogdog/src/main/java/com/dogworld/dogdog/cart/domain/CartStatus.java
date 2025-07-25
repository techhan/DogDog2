package com.dogworld.dogdog.cart.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CartStatus {
  ACTIVE("사용 가능"),
  INACTIVE("미사용"),
  EMPTY("빈 상태");

  private final String text;
}
