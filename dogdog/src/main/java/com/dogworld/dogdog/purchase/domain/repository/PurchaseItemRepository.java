package com.dogworld.dogdog.purchase.domain.repository;

import com.dogworld.dogdog.purchase.domain.PurchaseItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseItemRepository extends JpaRepository<PurchaseItem, Long> {

    boolean existsByProductIdAndPurchase_MemberId(Long productId, Long memberId);
}
