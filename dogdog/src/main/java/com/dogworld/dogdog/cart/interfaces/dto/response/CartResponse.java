package com.dogworld.dogdog.cart.interfaces.dto.response;

import com.dogworld.dogdog.cart.domain.Cart;
import com.dogworld.dogdog.cart.domain.CartStatus;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CartResponse {

  private Long cartId;
  private List<CartItemResponse> items;
  private CartStatus status;
  private BigDecimal totalPrice;

  public static CartResponse from(Cart cart, List<CartItemResponse> cartItemResponses, BigDecimal totalPrice) {
    return CartResponse.builder()
        .cartId(cart.getId())
        .items(cartItemResponses)
        .status(cart.getStatus())
        .totalPrice(totalPrice)
        .build();
  }

  public static CartResponse empty() {
    return CartResponse.builder()
        .cartId(null)
        .items(Collections.emptyList())
        .status(CartStatus.EMPTY)
        .totalPrice(BigDecimal.ZERO)
        .build();
  }
}
