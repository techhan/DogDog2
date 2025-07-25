package com.dogworld.dogdog.cart.domain.repository;

import com.dogworld.dogdog.cart.domain.CartItem;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

  Optional<CartItem> findByIdAndCartId(Long cartItemId, Long cartId);
}
