package com.dogworld.dogdog.purchase.application;

import com.dogworld.dogdog.purchase.domain.repository.PurchaseRepository;
import com.dogworld.dogdog.purchase.interfaces.dto.request.PurchaseFromCartRequest;
import com.dogworld.dogdog.purchase.interfaces.dto.response.PurchaseFromCartResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PurchaseCommandService {
  private final PurchaseRepository purchaseRepository;

  public PurchaseFromCartResponse purchaseFromCart(PurchaseFromCartRequest request) {
    return null ;
  }
}
