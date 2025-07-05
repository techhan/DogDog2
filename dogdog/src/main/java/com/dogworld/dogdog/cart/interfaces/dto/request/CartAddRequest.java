package com.dogworld.dogdog.cart.interfaces.dto.request;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartAddRequest {
  @NotNull(message = "{validation.memberId.required}")
  private Long memberId;

  private List<CartItemAddRequest> cartItems;
}
