package com.dogworld.dogdog.purchase.interfaces.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PurchaseFromCartRequest {

  @NotNull
  private Long cartId;

  @NotBlank
  private String shippingAddress;
}
