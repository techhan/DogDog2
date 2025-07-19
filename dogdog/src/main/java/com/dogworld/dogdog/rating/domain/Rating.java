package com.dogworld.dogdog.rating.domain;

import com.dogworld.dogdog.common.domain.BaseEntity;
import com.dogworld.dogdog.member.domain.Member;
import com.dogworld.dogdog.product.domain.Product;
import com.dogworld.dogdog.rating.interfaces.dto.request.RatingRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@Table(name = "ratings")
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
    @Column(nullable = false)
    private int score;

    @Column(length = 1000)
    private String comment;

    @Column(name = "is_deleted", nullable = false)
    private boolean deleted = false;

    @Builder
    private Rating(Product product, Member member, int score, String comment, boolean deleted) {
        this.product = product;
        this.member = member;
        this.score = score;
        this.comment = comment;
        this.deleted = deleted;
    }
    public static Rating create(RatingRequest request, Member member, Product product) {
        return Rating.builder()
                .product(product)
                .member(member)
                .score(request.getScore())
                .comment(request.getComment())
                .deleted(false)
                .build();
    }
}
