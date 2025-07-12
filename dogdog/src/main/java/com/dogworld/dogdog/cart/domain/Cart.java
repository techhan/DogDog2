package com.dogworld.dogdog.cart.domain;

import com.dogworld.dogdog.cart.interfaces.dto.response.CartResponse;
import com.dogworld.dogdog.common.domain.BaseEntity;
import com.dogworld.dogdog.member.domain.Member;
import com.dogworld.dogdog.product.domain.Product;
import jakarta.persistence.CascadeType;
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
import jakarta.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id", nullable = false)
  private Member member;

  @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<CartItem> cartItems;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private CartStatus status;

  private Cart(Member member, List<CartItem> cartItems) {
    this.member = member;
    this.status = CartStatus.ACTIVE;
    this.cartItems = cartItems.isEmpty() ? new ArrayList<>() : cartItems;
  }

  public static Cart create(Member member) {
    return new Cart(member, new ArrayList<>());
  }

  public static Cart create(Member member, List<CartItem> cartItems) {
    return new Cart(member, cartItems);
  }

  public BigDecimal getTotalPrice() {
    return cartItems.stream()
        .map(CartItem::getTotalPrice)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  public void clearItems() {
    this.cartItems.clear();
  }
  
  public void addOrUpdateItem(Product product, int quantity) {
    Optional<CartItem> existing = cartItems.stream()
        .filter(ci -> ci.getProduct().equals(product))
        .findFirst();
    
    if(existing.isPresent()) {
      existing.get().increaseQuantity(quantity);
    } else {
      this.cartItems.add(CartItem.create(this, product, quantity));
    }
  }

  public int getQuantity(Product product) {
    return cartItems.stream()
        .filter(ci -> ci.getProduct().equals(product))
        .mapToInt(CartItem::getQuantity)
        .findFirst().orElse(0);
  }

  public void removeItem(CartItem cartItem) {
    this.cartItems.remove(cartItem);
    cartItem.setCart(null);
  }
}
