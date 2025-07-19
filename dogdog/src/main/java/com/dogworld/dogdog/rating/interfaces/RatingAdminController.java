package com.dogworld.dogdog.rating.interfaces;

import com.dogworld.dogdog.global.common.response.ApiResponse;
import com.dogworld.dogdog.rating.application.RatingQueryService;
import com.dogworld.dogdog.rating.interfaces.dto.response.RatingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ratings")
public class RatingAdminController {
    private final RatingQueryService ratingQueryService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<RatingResponse>>> getAllRatings() {
        List<RatingResponse> responses = ratingQueryService.getAllRatings();
        return ResponseEntity.ok(ApiResponse.success(responses));
    }
}
