package com.dogworld.dogdog.cart.application;

import com.dogworld.dogdog.cart.domain.Cart;
import com.dogworld.dogdog.cart.domain.CartStatus;
import com.dogworld.dogdog.cart.domain.repository.CartRepository;
import com.dogworld.dogdog.cart.interfaces.dto.request.CartRequest;
import com.dogworld.dogdog.cart.interfaces.dto.response.CartItemResponse;
import com.dogworld.dogdog.cart.interfaces.dto.response.CartResponse;
import com.dogworld.dogdog.global.error.code.ErrorCode;
import com.dogworld.dogdog.global.error.exception.CustomException;
import com.dogworld.dogdog.member.domain.Member;
import com.dogworld.dogdog.member.domain.repository.MemberRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartQueryService {

  private final MemberRepository memberRepository;
  private final CartRepository cartRepository;

  public CartResponse getAllCartItems(CartRequest request) {
    Member member = memberRepository.findById(request.getMemberId())
        .orElseThrow(() -> new CustomException(
            ErrorCode.NOT_FOUND_USER));
    return cartRepository.findByMemberAndStatus(member, CartStatus.ACTIVE)
        .map(cart -> {
          List<CartItemResponse> cartItemResponses = cart.getCartItems().stream()
              .map(CartItemResponse::from)
              .toList();

          BigDecimal totalPrice = cartItemResponses.stream()
              .map(CartItemResponse::getTotalPrice)
              .reduce(BigDecimal.ZERO, BigDecimal::add);

          return CartResponse.from(cart, cartItemResponses, totalPrice);
        })
        .orElseGet(CartResponse::empty);
  }
}
