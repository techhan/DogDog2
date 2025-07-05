package com.dogworld.dogdog.cart.interfaces.dto.response;

import com.dogworld.dogdog.cart.domain.CartItem;
import com.dogworld.dogdog.cart.domain.CartStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;
import java.util.Collections;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CartItemResponse {
  private Long cartItemId;
  private Long productId;
  private String productName;
  private int quantity;
  private BigDecimal price;

  @JsonIgnore
  public BigDecimal getTotalPrice() {
    return price.multiply(new BigDecimal(quantity));
  }

  public static CartItemResponse from(CartItem cartItem) {
    return CartItemResponse.builder()
        .cartItemId(cartItem.getId())
        .productId(cartItem.getProduct().getId())
        .productName(cartItem.getProduct().getName())
        .quantity(cartItem.getQuantity())
        .price(cartItem.getPrice())
        .build();
  }
}
