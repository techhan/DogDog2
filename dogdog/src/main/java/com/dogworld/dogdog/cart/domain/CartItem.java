package com.dogworld.dogdog.cart.domain;

import com.dogworld.dogdog.common.domain.BaseEntity;
import com.dogworld.dogdog.product.domain.Product;
import jakarta.persistence.*;

import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Table(name = "cart_items")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartItem extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Setter
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "cart_id", nullable = false)
  private Cart cart;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id", nullable = false)
  private Product product;

  @Column(nullable = false)
  private int quantity;

  @Column(nullable = false, precision = 18, scale = 2)
  private BigDecimal price;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private CartItemStatus status;

  private CartItem(Cart cart, Product product, int quantity) {
    this.cart = cart;
    this.product = product;
    this.quantity = quantity;
    this.price = product.getPrice();
    this.status = CartItemStatus.ACTIVE;
  }

  public static CartItem create(Cart cart, Product product, int quantity) {
    return new CartItem(cart, product, quantity);
  }

  public void increaseQuantity(int quantity) {
    this.quantity += quantity;
  }

  public BigDecimal getTotalPrice() {
    return price.multiply(BigDecimal.valueOf(quantity));
  }

}
