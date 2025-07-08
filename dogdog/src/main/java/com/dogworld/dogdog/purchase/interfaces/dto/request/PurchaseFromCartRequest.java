package com.dogworld.dogdog.purchase.interfaces.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PurchaseFromCartRequest {

  @NotNull
  private Long cartId;

  @NotBlank
  private String shippingAddress;
}
