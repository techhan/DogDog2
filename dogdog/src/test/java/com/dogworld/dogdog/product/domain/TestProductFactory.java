package com.dogworld.dogdog.product.domain;

import com.dogworld.dogdog.category.domain.Category;
import com.dogworld.dogdog.product.interfaces.dto.request.ProductRequest;
import java.util.UUID;

public class TestProductFactory {

  public static Product createProduct(Category category) {

    String unique = UUID.randomUUID().toString().substring(0, 8);

    ProductRequest request = ProductRequest.builder()
        .name("상품" + unique)
        .description("상품입니다")
        .price(10000)
        .stock(10)
        .categoryId(category.getId())
        .build();

    return Product.create(request, category);
  }
}
