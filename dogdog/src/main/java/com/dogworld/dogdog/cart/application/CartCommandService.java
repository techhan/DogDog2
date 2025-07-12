package com.dogworld.dogdog.cart.application;

import com.dogworld.dogdog.cart.domain.Cart;
import com.dogworld.dogdog.cart.domain.CartItem;
import com.dogworld.dogdog.cart.domain.CartStatus;
import com.dogworld.dogdog.cart.domain.repository.CartItemRepository;
import com.dogworld.dogdog.cart.domain.repository.CartRepository;
import com.dogworld.dogdog.cart.interfaces.dto.request.CartItemAddRequest;
import com.dogworld.dogdog.cart.interfaces.dto.request.CartAddRequest;
import com.dogworld.dogdog.cart.interfaces.dto.request.CartRequest;
import com.dogworld.dogdog.cart.interfaces.dto.response.CartItemAddResponse;
import com.dogworld.dogdog.global.error.code.ErrorCode;
import com.dogworld.dogdog.global.error.detail.StockExceptionDetail;
import com.dogworld.dogdog.global.error.exception.CustomException;
import com.dogworld.dogdog.member.domain.Member;
import com.dogworld.dogdog.member.domain.repository.MemberRepository;
import com.dogworld.dogdog.product.domain.Product;
import com.dogworld.dogdog.product.domain.repository.ProductRepository;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CartCommandService {

  private final CartRepository cartRepository;
  private final MemberRepository  memberRepository;
  private final ProductRepository productRepository;
  private final CartItemRepository cartItemRepository;

  public List<CartItemAddResponse> addItems(CartAddRequest request) {
    Member member = getMember(request.getMemberId());
    Cart cart = getOrCreateCart(member);

    Map<Long, Product> productMap = getProductMap(request.getCartItems());

    for(CartItemAddRequest itemRequest : request.getCartItems()) {
      Product product = productMap.get(itemRequest.getProductId());
      int quantity = itemRequest.getQuantity();
      int totalQty = cart.getQuantity(product) + quantity;

      validateStock(product, totalQty);
      cart.addOrUpdateItem(product, quantity);
    }

    return cart.getCartItems().stream()
        .map(CartItemAddResponse::from)
        .toList();
  }

  public void deleteItem(Long cartItemId, CartRequest request) {
    Member member = getMember(request.getMemberId());
    Cart cart = getCart(member);
    CartItem cartItem = getCartItem(cartItemId, cart.getId());

    validateCartItemOwnership(cartItem, member);

    cartItemRepository.delete(cartItem);
    cart.removeItem(cartItem);
  }

  private void validateCartItemOwnership(CartItem cartItem, Member member) {
    if(isNotCartItemOwner(cartItem, member)) {
      throw new CustomException(ErrorCode.CART_ITEM_NOT_BELONG_TO_MEMBER);
    }
  }

  public void deleteItems(Long cartId, CartRequest request) {
    Member member = getMember(request.getMemberId());
    Cart cart = getCartAllItems(member);
    if(isDifferentCartId(cartId, cart)) {
      throw new CustomException(ErrorCode.CART_NOT_BELONG_TO_MEMBER);
    }

    cart.clearItems();
  }

  private  boolean isDifferentCartId(Long cartId, Cart cart) {
    return !cartId.equals(cart.getId());
  }

  private boolean isNotCartItemOwner(CartItem cartItem, Member member) {
    return !cartItem.getCart().getMember().equals(member);
  }

  private CartItem getCartItem(Long cartItemId, Long cartId) {
    return cartItemRepository.findByIdAndCartId(cartItemId, cartId)
        .orElseThrow(() -> new CustomException(ErrorCode.INVALID_CART_ITEM_FOR_DELETE));
  }

  private Member getMember(Long memberId) {
    return memberRepository.findById(memberId)
        .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
  }

  private Cart getOrCreateCart(Member member) {
    return cartRepository.findByMemberAndStatus(member, CartStatus.ACTIVE)
        .orElseGet(() -> cartRepository.save(Cart.create(member)));
  }

  private Map<Long, Product> getProductMap(List<CartItemAddRequest> cartItemsRequest) {
    List<Long> productIds = cartItemsRequest.stream()
        .map(CartItemAddRequest::getProductId)
        .toList();

    return productRepository.findAllById(productIds)
        .stream().collect(Collectors.toMap(Product::getId, Function.identity()));
  }

  private void validateStock(Product product, int newQuantity) {
    if(product.getStock() < newQuantity) {
      throw new CustomException(ErrorCode.NOT_ENOUGH_PRODUCT_STOCK
          , new StockExceptionDetail(product.getStock(), newQuantity));
    }
  }

  private Cart getCart(Member member) {
    return cartRepository.findByMemberAndStatus(member, CartStatus.ACTIVE)
        .orElseThrow(() -> new CustomException(ErrorCode.CART_NOT_FOUND));
  }

  private Cart getCartAllItems(Member member) {
    return cartRepository.findByMemberAndStatusWithItems(member, CartStatus.ACTIVE)
        .orElseThrow(() -> new CustomException(ErrorCode.CART_NOT_FOUND));
  }
}
