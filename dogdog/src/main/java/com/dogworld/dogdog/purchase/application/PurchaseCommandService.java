package com.dogworld.dogdog.purchase.application;

import com.dogworld.dogdog.cart.domain.Cart;
import com.dogworld.dogdog.cart.domain.CartItem;
import com.dogworld.dogdog.cart.domain.repository.CartRepository;
import com.dogworld.dogdog.global.error.code.ErrorCode;
import com.dogworld.dogdog.global.error.detail.StockExceptionDetail;
import com.dogworld.dogdog.global.error.exception.CustomException;
import com.dogworld.dogdog.member.domain.Member;
import com.dogworld.dogdog.product.domain.Product;
import com.dogworld.dogdog.purchase.domain.Purchase;
import com.dogworld.dogdog.purchase.domain.repository.PurchaseRepository;
import com.dogworld.dogdog.purchase.interfaces.dto.request.PurchaseFromCartRequest;
import com.dogworld.dogdog.purchase.interfaces.dto.response.PurchaseFromCartResponse;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PurchaseCommandService {
  private final PurchaseRepository purchaseRepository;
  private final CartRepository cartRepository;

  public PurchaseFromCartResponse purchaseFromCart(PurchaseFromCartRequest request) {
    Cart cart = getCart(request);
    Member member = cart.getMember();
    List<CartItem> cartItems = cart.getCartItems();

    validateStockItems(cartItems);
    productDecreaseStock(cartItems);

    Purchase createdPurchase = Purchase.create(request, member, cartItems, LocalDateTime.now());
    return PurchaseFromCartResponse.from(createdPurchase);
  }

  private static void productDecreaseStock(List<CartItem> cartItems) {
    cartItems
        .forEach(item -> item.getProduct().decreaseStock(item.getQuantity()));
  }

  private void validateStockItems(List<CartItem> cartItems) {
    cartItems
        .forEach(item -> validateStock(item.getProduct(), item.getQuantity()));
  }

  private Cart getCart(PurchaseFromCartRequest request) {
    return cartRepository.findById(request.getCartId())
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_CART));
  }

  private void validateStock(Product product, int quantity) {
    if(product.getStock() < quantity) {
      throw new CustomException(ErrorCode.NOT_ENOUGH_PRODUCT_STOCK
          , new StockExceptionDetail(product.getStock(), quantity));
    }
  }
}
