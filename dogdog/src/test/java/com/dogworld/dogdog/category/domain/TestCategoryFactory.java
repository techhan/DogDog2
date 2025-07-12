package com.dogworld.dogdog.category.domain;

import com.dogworld.dogdog.category.interfaces.dto.request.CategoryRequest;
import java.util.UUID;

public class TestCategoryFactory {

  public static Category createCategory(Category parent) {

    String unique = UUID.randomUUID().toString().substring(0, 8);
    CategoryRequest request = CategoryRequest.builder()
        .name("카테고리" + unique)
        .parentId(parent == null ? null : parent.getId())
        .build();

    return Category.create(request, parent);
  }

}
