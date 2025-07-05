package com.dogworld.dogdog.cart.interfaces.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartItemAddRequest {
  private Long productId;
  private int quantity;
}
