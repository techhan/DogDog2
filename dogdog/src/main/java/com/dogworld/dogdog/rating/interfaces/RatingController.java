package com.dogworld.dogdog.rating.interfaces;

import com.dogworld.dogdog.global.common.response.ApiResponse;
import com.dogworld.dogdog.rating.application.RatingCommandService;
import com.dogworld.dogdog.rating.application.RatingQueryService;
import com.dogworld.dogdog.rating.interfaces.dto.request.RatingRequest;
import com.dogworld.dogdog.rating.interfaces.dto.response.RatingResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products/")
public class RatingController {

    private final RatingCommandService ratingCommandService;
    private final RatingQueryService ratingQueryService;

    @PostMapping("/{productId}/ratings")
    public ResponseEntity<ApiResponse<RatingResponse>> createRating(@Valid @RequestBody RatingRequest request,
                                                                    @PathVariable Long productId) {
        RatingResponse response = ratingCommandService.createRating(request, productId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
