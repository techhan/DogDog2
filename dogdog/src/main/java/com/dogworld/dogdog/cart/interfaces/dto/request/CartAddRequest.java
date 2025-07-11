package com.dogworld.dogdog.cart.interfaces.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartAddRequest {
  @NotNull(message = "{validation.memberId.required}")
  private Long memberId;

  @Valid
  private List<CartItemAddRequest> cartItems;
}
