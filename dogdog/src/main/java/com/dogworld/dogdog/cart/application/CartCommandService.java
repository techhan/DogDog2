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

    List<CartItemAddRequest> cartItemsRequest = request.getCartItems();
    List<Long> productIds = extractProductIds(cartItemsRequest);
    Map<Long, Product> productMap = getProductMap(productIds);

    List<CartItem> cartItems = applyCartItemsFromRequest(cart, cartItemsRequest, productMap);

    return cartItems.stream()
        .map(CartItemAddResponse::from)
        .toList();
  }

  public void deleteItem(Long cartItemId, CartRequest request) {
    Member member = getMember(request.getMemberId());
    Cart cart = getCart(member);
    CartItem cartItem = getCartItem(cartItemId, cart.getId());

    if(isNotCartItemOwner(cartItem, member)) {
      throw new CustomException(ErrorCode.CART_ITEM_NOT_BELONG_TO_MEMBER);
    }

    cartItemRepository.delete(cartItem);
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
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_CART_ITEM));
  }

  private Member getMember(Long memberId) {
    return memberRepository.findById(memberId)
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
  }

  private Cart getOrCreateCart(Member member) {
    return cartRepository.findByMemberAndStatus(member, CartStatus.ACTIVE)
        .orElseGet(() -> cartRepository.save(Cart.create(member)));
  }

  private List<CartItem> applyCartItemsFromRequest(Cart cart, List<CartItemAddRequest> cartItemsRequest,
      Map<Long, Product> productMap) {
    List<CartItem> cartItems = cart.getCartItems();
    for(CartItemAddRequest cartItemAddRequest : cartItemsRequest) {
      Product product = getProductFromMap(cartItemAddRequest, productMap);
      addOrUpdateCartItem(cartItemAddRequest, cartItems, product, cart);
    }
    return cartItems;
  }

  private void addOrUpdateCartItem(CartItemAddRequest cartItemAddRequest, List<CartItem> cartItems,
                                    Product product, Cart cart) {
    int quantity = cartItemAddRequest.getQuantity();

    Optional<CartItem> existingItem = hadProduct(cartItems, product);

    if(existingItem.isPresent()) {
      int newQuantity = calculateNewQuantity(existingItem, quantity);
      validateStock(product, newQuantity);

      existingItem.get().increaseQuantity(quantity);
    } else {
      cartItems.add(CartItem.create(cart, product, quantity));
    }
  }

  private Map<Long, Product> getProductMap(List<Long> productIds) {
    return productRepository.findAllById(productIds)
        .stream().collect(Collectors.toMap(Product::getId, Function.identity()));
  }


  private List<Long> extractProductIds(List<CartItemAddRequest> cartItemsRequest) {
    return cartItemsRequest.stream()
        .map(CartItemAddRequest::getProductId)
        .toList();
  }

  private void validateStock(Product product, int newQuantity) {
    if(product.getStock() < newQuantity) {
      throw new CustomException(ErrorCode.NOT_ENOUGH_PRODUCT_STOCK
          , new StockExceptionDetail(product.getStock(), newQuantity));
    }
  }

  private int calculateNewQuantity(Optional<CartItem> existingItem, int quantity) {
    return existingItem.get().getQuantity() + quantity;
  }

  private Optional<CartItem> hadProduct(List<CartItem> cartItems, Product product) {
    return cartItems.stream()
        .filter(ci -> ci.getProduct().equals(product))
        .findFirst();
  }

  private Product getProductFromMap(CartItemAddRequest cartItemAddRequest,
      Map<Long, Product> productMap) {
    return productMap.get(cartItemAddRequest.getProductId());
  }

  private Cart getCart(Member member) {
    return cartRepository.findByMemberAndStatus(member, CartStatus.ACTIVE)
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_CART));
  }

  private Cart getCartAllItems(Member member) {
    return cartRepository.findByMemberAndStatusWithItems(member, CartStatus.ACTIVE)
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_CART));
  }
}
