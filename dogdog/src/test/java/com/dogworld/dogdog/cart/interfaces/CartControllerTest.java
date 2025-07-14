package com.dogworld.dogdog.cart.interfaces;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dogworld.dogdog.cart.domain.Cart;
import com.dogworld.dogdog.cart.domain.CartItem;
import com.dogworld.dogdog.cart.domain.CartStatus;
import com.dogworld.dogdog.cart.domain.repository.CartItemRepository;
import com.dogworld.dogdog.cart.domain.repository.CartRepository;
import com.dogworld.dogdog.cart.interfaces.dto.request.CartAddRequest;
import com.dogworld.dogdog.cart.interfaces.dto.request.CartItemAddRequest;
import com.dogworld.dogdog.cart.interfaces.dto.request.CartRequest;
import com.dogworld.dogdog.cart.interfaces.dto.response.CartItemResponse;
import com.dogworld.dogdog.cart.interfaces.dto.response.CartResponse;
import com.dogworld.dogdog.category.domain.Category;
import com.dogworld.dogdog.category.domain.TestCategoryFactory;
import com.dogworld.dogdog.category.domain.repository.CategoryRepository;
import com.dogworld.dogdog.member.domain.Member;
import com.dogworld.dogdog.member.domain.TestMemberFactory;
import com.dogworld.dogdog.member.domain.repository.MemberRepository;
import com.dogworld.dogdog.product.domain.Product;
import com.dogworld.dogdog.product.domain.TestProductFactory;
import com.dogworld.dogdog.product.domain.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.logging.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
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
  private CategoryRepository categoryRepository;

  private Member member;
  private Product product1;
  private Product product2;
  private Product product3;
  @Autowired
  private CartItemRepository cartItemRepository;

  @BeforeEach
  void setUp() {
    // 회원
    member = TestMemberFactory.createMember();
    memberRepository.saveAndFlush(member);

    // 카테고리
    Category category = TestCategoryFactory.createCategory(null);
    categoryRepository.saveAndFlush(category);

    // 상품
    product1 = TestProductFactory.createProduct(category);
    product2 = TestProductFactory.createProduct(category);
    product3 = TestProductFactory.createProduct(category);

    List<Product> items = List.of(product1, product2, product3);
    productRepository.saveAllAndFlush(items);
  }

  @DisplayName("장바구니에 상품을 추가한다.")
  @Test
  void should_add_cart_item_when_request_is_valid() throws Exception {
    // given
    CartItemAddRequest item1 = CartItemAddRequest.builder()
        .productId(product1.getId())
        .quantity(2)
        .build();
    CartItemAddRequest item2 = CartItemAddRequest.builder()
        .productId(product2.getId())
        .quantity(2)
        .build();
    List<CartItemAddRequest> items = List.of(item1, item2);

    CartAddRequest request = CartAddRequest.builder()
        .memberId(member.getId())
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
        .andExpect(jsonPath("$.message.[0].productId").value(product1.getId()))
        .andExpect(jsonPath("$.message.[1].productId").value(product2.getId()));
  }

  @DisplayName("존재하지 않는 회원 장바구니에 상품을 추가하면 예외가 발생한다.")
  @Test
  void should_throw_exception_when_user_not_found() throws Exception {
    // given
    CartItemAddRequest item1 = CartItemAddRequest.builder()
        .productId(product1.getId())
        .quantity(2)
        .build();
    CartItemAddRequest item2 = CartItemAddRequest.builder()
        .productId(product2.getId())
        .quantity(2)
        .build();
    List<CartItemAddRequest> items = List.of(item1, item2);

    CartAddRequest request = CartAddRequest.builder()
        .memberId(member.getId() + 3)
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
        .productId(product1.getId())
        .quantity(1)
        .build();
    CartItemAddRequest item2 = CartItemAddRequest.builder()
        .productId(product2.getId())
        .quantity(0)
        .build();
    List<CartItemAddRequest> items = List.of(item1, item2);

    CartAddRequest request = CartAddRequest.builder()
        .memberId(member.getId())
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
    Cart cart = Cart.create(member);
    cartRepository.saveAndFlush(cart);

    cart.addOrUpdateItem(product1, 1);
    cart.addOrUpdateItem(product2, 3);
    cart.addOrUpdateItem(product3, 4);

    // when & then
      mockMvc.perform(get("/api/carts/" + member.getId())
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.result").value(true))
          .andExpect(jsonPath("$.message.items.length()").value(cart.getCartItems().size()));
  }

  @DisplayName("장바구니에 담긴 상품을 삭제한다.")
  @Test
  void should_delete_cart_item_when_request_is_valid() throws Exception {
    // given
    Cart cart = Cart.create(member);
    cartRepository.saveAndFlush(cart);

    cart.addOrUpdateItem(product1, 1);
    cart.addOrUpdateItem(product2, 3);
    cart.addOrUpdateItem(product3, 4);
    cartRepository.flush();
    cartItemRepository.flush();

    Long itemId = cart.getCartItems().get(0).getId();
    CartRequest request = CartRequest.builder().memberId(member.getId()).build();

    String jsonString = objectMapper.writeValueAsString(request);

    // when & then
    mockMvc.perform(delete("/api/carts/items/" + itemId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonString)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result").value(true));

    mockMvc.perform(get("/api/carts/" + member.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result").value(true))
        .andExpect(jsonPath("$.message.items.length()").value(2));

  }


}