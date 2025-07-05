package com.dogworld.dogdog.cart.interfaces.dto.request;

import com.dogworld.dogdog.cart.domain.CartItemStatus;
import java.math.BigDecimal;
import lombok.Getter;

@Getter
public class CartItemRequest {
  private Long productId;
  private int quantity;
  private BigDecimal price;
  private BigDecimal totalPrice;
  private CartItemStatus status;
}
