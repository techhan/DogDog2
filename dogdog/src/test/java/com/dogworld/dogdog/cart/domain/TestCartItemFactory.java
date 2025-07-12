package com.dogworld.dogdog.cart.domain;

import com.dogworld.dogdog.cart.interfaces.dto.request.CartItemAddRequest;
import com.dogworld.dogdog.product.domain.Product;

public class TestCartItemFactory {

  public static CartItem createCartItem(Cart cart, Product product, int quantity) {
    return CartItem.create(cart, product, quantity);
  }
}
