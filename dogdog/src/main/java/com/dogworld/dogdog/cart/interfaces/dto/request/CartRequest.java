package com.dogworld.dogdog.cart.interfaces.dto.request;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartRequest {
  private Long memberId;
  private List<CartItemAddRequest> cartItems;
}
