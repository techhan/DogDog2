package com.dogworld.dogdog.cart.application;


import com.dogworld.dogdog.cart.domain.Cart;
import com.dogworld.dogdog.cart.domain.TestCartFactory;
import com.dogworld.dogdog.cart.domain.repository.CartRepository;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartQueryServiceTest {

    @InjectMocks
    private CartQueryService cartQueryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private ProductRepository productRepository;

    private Member testMember;
    private Product testProduct1;
    private Product testProduct2;
    private Product testProduct3;
    private Cart testCart;


    @BeforeEach
    void setUp() {
        // 회원
        testMember = TestMemberFactory.createMember();
        ReflectionTestUtils.setField(testMember, "id", 1L);

        // 카테고리
        Category category = TestCategoryFactory.createCategory(null);
        ReflectionTestUtils.setField(category, "id", 1L);

        // 상품
        testProduct1 = TestProductFactory.createProduct(category);
        testProduct2 = TestProductFactory.createProduct(category);
        testProduct3 = TestProductFactory.createProduct(category);
        ReflectionTestUtils.setField(testProduct1, "id", 1L);
        ReflectionTestUtils.setField(testProduct2, "id", 1L);
        ReflectionTestUtils.setField(testProduct3, "id", 1L);

        // 장바구니
        testCart = TestCartFactory.createCart(testMember, new ArrayList<>());
        testCart.addOrUpdateItem(testProduct1, 2);
        testCart.addOrUpdateItem(testProduct2, 2);
        testCart.addOrUpdateItem(testProduct3, 2);

    }

    @DisplayName("회원의 장바구니에 있는 모든 상품을 조회한다.")
    @Test
    void getAllCartItems() {
        CartResponse allCartItems = cartQueryService.getAllCartItems(testMember.getId());
        
    }
}