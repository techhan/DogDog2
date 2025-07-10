package com.dogworld.dogdog.purchase.domain;

import com.dogworld.dogdog.cart.domain.CartItem;
import com.dogworld.dogdog.common.domain.BaseEntity;
import com.dogworld.dogdog.member.domain.Member;
import com.dogworld.dogdog.purchase.interfaces.dto.request.PurchaseFromCartRequest;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Purchase extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id", nullable = false)
  private Member member;

  @OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<PurchaseItem> purchaseItems = new ArrayList<>();

  @Column(nullable = false, precision = 18, scale = 2)
  private BigDecimal totalPrice;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private PurchaseStatus status;

  @Column(nullable = false)
  private String shippingAddress;

  @Column(nullable = false)
  private LocalDateTime orderedAt;

  private LocalDateTime canceledAt;

  @Builder
  private Purchase(Member member, List<PurchaseItem> purchaseItems, BigDecimal totalPrice,
      PurchaseStatus status, LocalDateTime orderedAt, String shippingAddress) {
    this.member = member;
    this.purchaseItems = purchaseItems;
    this.totalPrice = totalPrice;
    this.status = status;
    this.orderedAt = orderedAt;
    this.shippingAddress = shippingAddress;
  }

  public static Purchase create(PurchaseFromCartRequest request, Member member
                              , List<CartItem> cartItems, LocalDateTime orderedAt) {
    Purchase createdPurchase = Purchase.builder()
        .member(member)
        .purchaseItems(new ArrayList<>())
        .status(PurchaseStatus.CREATED)
        .shippingAddress(request.getShippingAddress())
        .orderedAt(orderedAt)
        .build();

    List<PurchaseItem> purchaseItems = cartItemToPurchaseItem(createdPurchase, cartItems);
    createdPurchase.purchaseItems.addAll(purchaseItems);
    createdPurchase.totalPrice = calculateTotalPrice(purchaseItems);

    return createdPurchase;
  }

  private static List<PurchaseItem> cartItemToPurchaseItem(Purchase purchase, List<CartItem> cartItems) {
    return cartItems.stream()
        .map(cartItem -> PurchaseItem.ofCartItem(purchase, cartItem))
        .toList();
  }

  private static BigDecimal calculateTotalPrice(List<PurchaseItem> purchaseItems) {
    return purchaseItems.stream()
        .map(PurchaseItem::getTotalPrice)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  public void markAsOrdered() {
    this.status = PurchaseStatus.ORDERED;
    this.orderedAt = LocalDateTime.now();
    this.purchaseItems.forEach(PurchaseItem::markAsOrdered);
  }
}
