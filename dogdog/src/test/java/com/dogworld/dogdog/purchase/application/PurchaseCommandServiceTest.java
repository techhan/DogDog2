package com.dogworld.dogdog.purchase.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.dogworld.dogdog.cart.domain.Cart;
import com.dogworld.dogdog.cart.domain.TestCartFactory;
import com.dogworld.dogdog.cart.domain.repository.CartRepository;
import com.dogworld.dogdog.category.domain.Category;
import com.dogworld.dogdog.category.domain.TestCategoryFactory;
import com.dogworld.dogdog.global.error.exception.CustomException;
import com.dogworld.dogdog.member.domain.Member;
import com.dogworld.dogdog.member.domain.TestMemberFactory;
import com.dogworld.dogdog.product.domain.Product;
import com.dogworld.dogdog.product.domain.TestProductFactory;
import com.dogworld.dogdog.product.domain.repository.ProductRepository;
import com.dogworld.dogdog.purchase.domain.Purchase;
import com.dogworld.dogdog.purchase.domain.repository.PurchaseRepository;
import com.dogworld.dogdog.purchase.interfaces.dto.request.PurchaseFromCartRequest;
import com.dogworld.dogdog.purchase.interfaces.dto.response.PurchaseFromCartResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class PurchaseCommandServiceTest {

  @InjectMocks
  private PurchaseCommandService purchaseCommandService;

  @Mock
  private CartRepository cartRepository;

  @Mock
  private PurchaseRepository purchaseRepository;

  @Mock
  private ProductRepository productRepository;

  private Member member;
  private Product product1;
  private Product product2;
  private Cart cart;

  @BeforeEach
  void setUp() {
    member = TestMemberFactory.createMember();
    ReflectionTestUtils.setField(member, "id", 1L);

    Category category = TestCategoryFactory.createCategory(null);

    product1 = TestProductFactory.createProduct(category);
    product2 = TestProductFactory.createProduct(category);
    ReflectionTestUtils.setField(product1, "id", 1L);
    ReflectionTestUtils.setField(product2, "id", 2L);

    cart = TestCartFactory.createCart(member,new ArrayList<>());
    ReflectionTestUtils.setField(cart, "id", 1L);
  }

  @DisplayName("장바구니에 담겨있는 모든 아이템에 대해 주문한다.")
  @Test
  void should_create_purchase_when_cart_is_valid() {
    // given
    cart.addOrUpdateItem(product1, 2);
    cart.addOrUpdateItem(product2, 2);

    PurchaseFromCartRequest request = PurchaseFromCartRequest
        .builder()
        .cartId(cart.getId())
        .shippingAddress("어쩌구 저쩌동")
        .build();

    when(cartRepository.findById(cart.getId())).thenReturn(Optional.of(cart));
    when(purchaseRepository.saveAndFlush(any())).thenAnswer(invocation -> invocation.getArgument(0));

    // when
    PurchaseFromCartResponse response = purchaseCommandService.purchaseFromCart(
        request);

    // then
    assertThat(response).isNotNull();
    assertThat(response.getTotalPrice()).isEqualTo(BigDecimal.valueOf(40000));
    assertThat(product1.getStock()).isEqualTo(8);
    assertThat(product2.getStock()).isEqualTo(8);

    verify(cartRepository).findById(cart.getId());
    verify(purchaseRepository).saveAndFlush(any(Purchase.class));
  }

  @DisplayName("존재하지 않는 상품을 구매하면 PRODUCT_NOT_FOUND 예외가 발생한다.")
  @Test
  void should_fail_purchase_when_cart_is_not_found() {

    // given
    Long notExistCartId = 999L;
    PurchaseFromCartRequest request = PurchaseFromCartRequest.builder()
        .cartId(notExistCartId)
        .shippingAddress("테스트 주소")
        .build();

    when(cartRepository.findById(notExistCartId)).thenReturn(Optional.empty());

    // when & then
    assertThrows(CustomException.class, ()
        -> purchaseCommandService.purchaseFromCart(request));
  }
}