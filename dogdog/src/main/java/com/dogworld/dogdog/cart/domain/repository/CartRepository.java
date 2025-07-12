package com.dogworld.dogdog.cart.domain.repository;

import com.dogworld.dogdog.cart.domain.Cart;
import com.dogworld.dogdog.cart.domain.CartStatus;
import com.dogworld.dogdog.member.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

  Optional<Cart> findByMemberAndStatus(Member member, CartStatus cartStatus);

  @Query("SELECT c FROM Cart c JOIN FETCH c.cartItems WHERE c.member = :member AND c.status = :status")
  Optional<Cart> findByMemberAndStatusWithItems(Member member, CartStatus status);
}
