package com.dogworld.dogdog.purchase.domain.repository;

import com.dogworld.dogdog.purchase.domain.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

}
