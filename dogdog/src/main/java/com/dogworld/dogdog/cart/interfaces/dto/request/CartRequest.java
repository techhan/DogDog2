package com.dogworld.dogdog.cart.interfaces.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CartRequest {

  @NotNull(message = "{validation.memberId.required}")
  private Long memberId;
}
