package com.dogworld.dogdog.refund.domain;

import com.dogworld.dogdog.common.domain.BaseEntity;
import com.dogworld.dogdog.purchase.domain.PurchaseItem;
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
public class RefundItem extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "refund_id", nullable = false)
  private Refund refund;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "purchase_product_id", nullable = false)
  private PurchaseItem purchaseItem;

  @Column(nullable = false)
  private int refundQuantity;

  @Column(nullable = false)
  private BigDecimal refundAmount;

  @Enumerated(EnumType.STRING)
  private RefundType  refundType;

  private String reason;

  @Builder
  public RefundItem(Refund refund, PurchaseItem purchaseItem, int refundQuantity,
      BigDecimal refundAmount, RefundType refundType, String reason) {
    this.refund = refund;
    this.purchaseItem = purchaseItem;
    this.refundQuantity = refundQuantity;
    this.refundAmount = refundAmount;
    this.refundType = refundType;
    this.reason = reason;
  }
}

