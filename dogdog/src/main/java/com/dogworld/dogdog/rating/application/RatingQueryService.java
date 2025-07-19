package com.dogworld.dogdog.rating.application;

import com.dogworld.dogdog.rating.domain.Rating;
import com.dogworld.dogdog.rating.domain.repository.RatingRepository;
import com.dogworld.dogdog.rating.interfaces.dto.response.RatingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingQueryService {

    private final RatingRepository ratingRepository;

    public List<RatingResponse> getAllRatings() {
        List<Rating> ratings = ratingRepository.findAll();
        return ratings.stream()
                .map(RatingResponse::from)
                .toList();
    }
}
