package com.dogworld.dogdog.cart.interfaces;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dogworld.dogdog.cart.application.CartCommandService;
import com.dogworld.dogdog.cart.domain.Cart;
import com.dogworld.dogdog.cart.domain.repository.CartItemRepository;
import com.dogworld.dogdog.cart.domain.repository.CartRepository;
import com.dogworld.dogdog.cart.interfaces.dto.request.CartAddRequest;
import com.dogworld.dogdog.cart.interfaces.dto.request.CartItemAddRequest;
import com.dogworld.dogdog.member.domain.Member;
import com.dogworld.dogdog.member.domain.repository.MemberRepository;
import com.dogworld.dogdog.product.domain.Product;
import com.dogworld.dogdog.product.domain.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class CartControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private CartRepository cartRepository;

  @Autowired
  private CartItemRepository cartItemRepository;

  private Member member;
  private Cart cart;

  @BeforeEach
  void setUp() {
   // member = memberRepository.save(Member.create())
  }

  @DisplayName("장바구니에 상품을 추가한다.")
  @Test
  void should_add_cart_item_when_request_is_valid() throws Exception {
      // given
    CartItemAddRequest item1 = CartItemAddRequest.builder()
        .productId(4L)
        .quantity(2)
      .build();
    CartItemAddRequest item2 = CartItemAddRequest.builder()
      .productId(5L)
      .quantity(2)
      .build();
    List<CartItemAddRequest> items = List.of(item1, item2);

    CartAddRequest request = CartAddRequest.builder()
        .memberId(3L)
        .cartItems(items)
        .build();

    String requestBody = objectMapper.writeValueAsString(request);

    // when &  then
    mockMvc.perform(post("/api/carts/items")
          .contentType(MediaType.APPLICATION_JSON)
          .content(requestBody)
          .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result").value(true))
        .andExpect(jsonPath("$.message.[0].productId").value(4L))
        .andExpect(jsonPath("$.message.[1].productId").value(5L));
  }

  @DisplayName("존재하지 않는 회원 장바구니에 상품을 추가하면 예외가 발생한다.")
  @Test
  void should_throw_exception_when_user_not_found() throws Exception {
      // given
    CartItemAddRequest item1 = CartItemAddRequest.builder()
        .productId(4L)
        .quantity(2)
      .build();
    CartItemAddRequest item2 = CartItemAddRequest.builder()
      .productId(5L)
      .quantity(2)
      .build();
    List<CartItemAddRequest> items = List.of(item1, item2);

    CartAddRequest request = CartAddRequest.builder()
        .memberId(2L)
        .cartItems(items)
        .build();

    String requestBody = objectMapper.writeValueAsString(request);

    // when &  then
    mockMvc.perform(post("/api/carts/items")
          .contentType(MediaType.APPLICATION_JSON)
          .content(requestBody)
          .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.result").value(false))
        .andExpect(jsonPath("$.error.code").value("USER_NOT_FOUND"));
  }

  @DisplayName("장바구니에 담는 상품의 수량이 음수면 예외가 발생한다.")
  @Test
  void should_throw_exception_when_item_quantity_is_not_positive() throws Exception {
    // given
    CartItemAddRequest item1 = CartItemAddRequest.builder()
        .productId(4L)
        .quantity(1)
        .build();
    CartItemAddRequest item2 = CartItemAddRequest.builder()
        .productId(5L)
        .quantity(0)
        .build();
    List<CartItemAddRequest> items = List.of(item1, item2);

    CartAddRequest request = CartAddRequest.builder()
        .memberId(3L)
        .cartItems(items)
        .build();

    String requestBody = objectMapper.writeValueAsString(request);

    // when &  then
    mockMvc.perform(post("/api/carts/items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.result").value(false))
        .andExpect(jsonPath("$.error.code").value("INVALID_INPUT_VALUE"));
  }

  @DisplayName("장바구니에 담긴 전체 상품을 조회한다.")
  @Test
  void should_return_all_items_when_request_is_valid() throws Exception {
      // given


      // when

      // then
  }


}