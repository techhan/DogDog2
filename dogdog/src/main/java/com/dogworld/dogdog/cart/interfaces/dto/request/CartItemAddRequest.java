package com.dogworld.dogdog.cart.interfaces.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartItemAddRequest {
  @NotNull(message = "{validation.productId.required}")
  private Long productId;

  @NotNull(message = "{validation.cart.quantity.required}")
  @Positive(message = "{validation.cart.quantity.positive}")
  private int quantity;
}
