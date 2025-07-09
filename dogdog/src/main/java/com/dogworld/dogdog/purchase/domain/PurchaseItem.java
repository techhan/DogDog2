package com.dogworld.dogdog.purchase.domain;

import com.dogworld.dogdog.cart.domain.CartItem;
import com.dogworld.dogdog.common.domain.BaseEntity;
import com.dogworld.dogdog.product.domain.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PurchaseItem extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "purchase_id", nullable = false)
  private Purchase purchase;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id", nullable = false)
  private Product product;

  @Column(nullable = false)
  private int quantity;

  @Column(nullable = false, precision = 18, scale = 2)
  private BigDecimal price;

  @Column(nullable = false, precision = 18, scale = 2)
  private BigDecimal totalPrice;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private PurchaseItemStatus status;

  public static PurchaseItem ofCartItem(Purchase purchase, CartItem cartItem) {
    return PurchaseItem.builder()
        .purchase(purchase)
        .product(cartItem.getProduct())
        .quantity(cartItem.getQuantity())
        .price(cartItem.getPrice())
        .status(PurchaseItemStatus.ORDERING)
        .build();
  }

  @Builder
  private PurchaseItem(Purchase purchase, Product product, int quantity, BigDecimal price,
      PurchaseItemStatus status) {
    this.purchase = purchase;
    this.product = product;
    this.quantity = quantity;
    this.price = price;
    this.totalPrice = calculateTotalPrice(price, quantity);
    this.status = (status != null) ? status : PurchaseItemStatus.ORDERING;
  }

  private BigDecimal calculateTotalPrice(BigDecimal price, int quantity) {
    return price.multiply(BigDecimal.valueOf(quantity));
  }
}
