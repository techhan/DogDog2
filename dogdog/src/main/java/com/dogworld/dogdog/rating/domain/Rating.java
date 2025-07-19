package com.dogworld.dogdog.rating.domain;

import com.dogworld.dogdog.common.domain.BaseEntity;
import com.dogworld.dogdog.member.domain.Member;
import com.dogworld.dogdog.product.domain.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Rating extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "member_id")
    private Member member;

    @NotNull
    @Column(nullable = false, precision = 2, scale = 1)
    private BigDecimal score;

    @Column(length = 1000)
    private String comment;

    @Column(name = "is_deleted", nullable = false)
    private boolean deleted = false;
}
