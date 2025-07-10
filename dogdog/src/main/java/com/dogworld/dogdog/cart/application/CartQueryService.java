package com.dogworld.dogdog.cart.application;

import com.dogworld.dogdog.cart.domain.CartStatus;
import com.dogworld.dogdog.cart.domain.repository.CartRepository;
import com.dogworld.dogdog.cart.interfaces.dto.request.CartRequest;
import com.dogworld.dogdog.cart.interfaces.dto.response.CartItemResponse;
import com.dogworld.dogdog.cart.interfaces.dto.response.CartResponse;
import com.dogworld.dogdog.global.error.code.ErrorCode;
import com.dogworld.dogdog.global.error.exception.CustomException;
import com.dogworld.dogdog.member.domain.Member;
import com.dogworld.dogdog.member.domain.repository.MemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartQueryService {

  private final MemberRepository memberRepository;
  private final CartRepository cartRepository;

  public CartResponse getAllCartItems(CartRequest request) {
    Member member = getMember(request);

    return cartRepository.findByMemberAndStatus(member, CartStatus.ACTIVE)
        .map(cart -> {
          List<CartItemResponse> cartItemResponses = cart.getCartItems().stream()
              .map(CartItemResponse::from)
              .toList();

          return CartResponse.from(cart, cartItemResponses, cart.getTotalPrice());
        })
        .orElseGet(CartResponse::empty);
  }

  private Member getMember(CartRequest request) {
    return memberRepository.findById(request.getMemberId())
        .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
  }
}
