package com.dogworld.dogdog.cart.interfaces.dto.response;

import com.dogworld.dogdog.cart.domain.CartItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CartItemAddResponse {
  private Long productId;
  private int quantity;

  public static CartItemAddResponse from(CartItem cartItem) {
    return CartItemAddResponse.builder()
        .productId(cartItem.getProduct().getId())
        .quantity(cartItem.getQuantity())
        .build();
  }
}
