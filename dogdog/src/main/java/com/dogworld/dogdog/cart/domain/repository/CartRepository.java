package com.dogworld.dogdog.cart.domain.repository;

import com.dogworld.dogdog.cart.domain.Cart;
import com.dogworld.dogdog.cart.domain.CartStatus;
import com.dogworld.dogdog.member.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

  Optional<Cart> findByMemberAndStatus(Member member, CartStatus cartStatus);
}
