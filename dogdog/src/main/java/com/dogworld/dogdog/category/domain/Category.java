package com.dogworld.dogdog.category.domain;

import com.dogworld.dogdog.category.interfaces.dto.request.CategoryRequest;
import com.dogworld.dogdog.common.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 100)
  private String name;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_id")
  @JsonBackReference
  private Category parent;

  private int depth;

  private int sortOrder;

  @Column(nullable = false)
  private Boolean active;

  private Category(String name, Category parent, int sortOrder, Boolean active) {
    this.name = name;
    this.parent = parent;
    this.depth = (parent != null) ? parent.getDepth() + 1 : 0;
    this.sortOrder = (sortOrder != 0) ? sortOrder : 1;
    this.active = (active != null) ? active : false;
  }

  public static Category create(CategoryRequest request, Category parent) {
    return new Category(
        request.getName(),
        parent,
        (request.getSortOrder() != null) ? request.getSortOrder() : 1,
        request.getActive()
    );
  }

  public void update(CategoryRequest request, Category parent) {
    this.name = request.getName();
    this.parent = parent;
    this.depth = (parent != null) ? parent.getDepth() + 1 : 0;
    this.sortOrder = request.getSortOrder();
    this.active = request.getActive();
  }
}