package com.dogworld.dogdog.purchase.interfaces;

import com.dogworld.dogdog.cart.domain.Cart;
import com.dogworld.dogdog.cart.domain.TestCartFactory;
import com.dogworld.dogdog.cart.domain.repository.CartItemRepository;
import com.dogworld.dogdog.cart.domain.repository.CartRepository;
import com.dogworld.dogdog.category.domain.Category;
import com.dogworld.dogdog.category.domain.TestCategoryFactory;
import com.dogworld.dogdog.category.domain.repository.CategoryRepository;
import com.dogworld.dogdog.member.domain.Member;
import com.dogworld.dogdog.member.domain.TestMemberFactory;
import com.dogworld.dogdog.member.domain.repository.MemberRepository;
import com.dogworld.dogdog.product.domain.Product;
import com.dogworld.dogdog.product.domain.TestProductFactory;
import com.dogworld.dogdog.product.domain.repository.ProductRepository;
import com.dogworld.dogdog.purchase.domain.repository.PurchaseRepository;
import com.dogworld.dogdog.purchase.interfaces.dto.request.PurchaseFromCartRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class PurchaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private CartItemRepository cartItemRepository;


    private Member member;
    private Product product1;
    private Product product2;
    private Product product3;
    private Cart cart;


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

        cart = TestCartFactory.createCart(member, new ArrayList<>());

        cart.addOrUpdateItem(product1, 2);
        cart.addOrUpdateItem(product2, 2);
        cart.addOrUpdateItem(product3, 2);

        cartRepository.saveAndFlush(cart);
    }

    @DisplayName("회원 장바구니에 담긴 모든 상품을 주문한다.")
    @Test
    void should_order_all_cart_items_when_request_is_valid() throws Exception {
        // given
        PurchaseFromCartRequest request = PurchaseFromCartRequest.builder()
                .shippingAddress("서울특별시 어쩌구 저쩌동")
                .cartId(cart.getId())
                .build();

        String jsonString = objectMapper.writeValueAsString(request);

        // when & then
        mockMvc.perform(post("/api/purchases/from-cart")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(true))
                .andExpect(jsonPath("$.message.shippingAddress").value(request.getShippingAddress()));
    }
}