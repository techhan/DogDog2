package com.dogworld.dogdog.rating.domain.repository;

import com.dogworld.dogdog.rating.domain.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findAllByProductIdAndDeletedFalse(Long productId);
}