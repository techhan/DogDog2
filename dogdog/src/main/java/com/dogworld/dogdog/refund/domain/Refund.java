package com.dogworld.dogdog.refund.domain;

import com.dogworld.dogdog.common.domain.BaseEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "refunds")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Refund extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private RefundType refundType;

  private String reason;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private RefundStatus status;

  private LocalDateTime requestedAt;

  private LocalDateTime processedAt;

  @Builder
  public Refund(RefundType refundType, String reason, RefundStatus status,
      LocalDateTime requestedAt,
      LocalDateTime processedAt) {
    this.refundType = refundType;
    this.reason = reason;
    this.status = status;
    this.requestedAt = requestedAt;
    this.processedAt = processedAt;
  }
}
