package com.dogworld.dogdog.purchase.application;

import com.dogworld.dogdog.cart.domain.Cart;
import com.dogworld.dogdog.cart.domain.CartItem;
import com.dogworld.dogdog.cart.domain.repository.CartRepository;
import com.dogworld.dogdog.global.error.code.ErrorCode;
import com.dogworld.dogdog.global.error.detail.StockExceptionDetail;
import com.dogworld.dogdog.global.error.exception.CustomException;
import com.dogworld.dogdog.member.domain.Member;
import com.dogworld.dogdog.product.domain.Product;
import com.dogworld.dogdog.product.domain.repository.ProductRepository;
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
  private final ProductRepository productRepository;

  public PurchaseFromCartResponse purchaseFromCart(PurchaseFromCartRequest request) {
    Cart cart = getCart(request);

    validateCartNotEmpty(cart);
    Member member = cart.getMember();
    List<CartItem> cartItems = cart.getCartItems();

    validateProductsExist(cartItems);
    validateStockItems(cartItems);
    decreaseProductStocks(cartItems);

    Purchase createdPurchase = Purchase.create(request, member, cartItems, LocalDateTime.now());
    purchaseRepository.saveAndFlush(createdPurchase);

    // TODO : 결제 연동 로직
    // 주문 완료 후처리
    createdPurchase.markAsOrdered();
    cartItems.clear();
    return PurchaseFromCartResponse.from(createdPurchase);
  }

  private static void validateCartNotEmpty(Cart cart) {
    if(cart.getCartItems().isEmpty()) {
      throw new CustomException(ErrorCode.EMPTY_CART);
    }
  }

  private void validateProductsExist(List<CartItem> cartItems) {
    cartItems.forEach(item -> validateProductExists(item.getProduct().getId()));
  }

  private void validateStockItems(List<CartItem> cartItems) {
    cartItems
        .forEach(item -> validateStock(item.getProduct(), item.getQuantity()));
  }

  private static void decreaseProductStocks(List<CartItem> cartItems) {
    cartItems
        .forEach(item -> item.getProduct().decreaseStock(item.getQuantity()));
  }

  private Cart getCart(PurchaseFromCartRequest request) {
    return cartRepository.findById(request.getCartId())
        .orElseThrow(() -> new CustomException(ErrorCode.CART_NOT_FOUND));
  }

  private void validateStock(Product product, int quantity) {
    if(product.getStock() < quantity) {
      throw new CustomException(ErrorCode.NOT_ENOUGH_PRODUCT_STOCK
          , new StockExceptionDetail(product.getStock(), quantity));
    }
  }

  private void validateProductExists(long productId) {
    if(!productRepository.existsById(productId)) {
      throw new CustomException(ErrorCode.PRODUCT_NOT_FOUND);
    }
  }
}
