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
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartQueryService {

  private final MemberRepository memberRepository;
  private final CartRepository cartRepository;

  public CartResponse getAllCartItems(Long memberId) {
    Member member = getMember(memberId);
    Cart cart = getOrCreateCart(member);

    List<CartItemResponse> cartItemResponse = cart.getCartItems().stream()
        .map(CartItemResponse::from)
        .toList();

    return CartResponse.from(cart, cartItemResponse, cart.getTotalPrice());
  }

  private Cart getOrCreateCart(Member member) {
    return cartRepository.findByMemberAndStatus(member, CartStatus.ACTIVE)
        .orElseGet(() -> cartRepository.save(Cart.create(member)));
  }

  private Member getMember(Long memberId) {
    return memberRepository.findById(memberId)
        .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
  }
}
