package com.dogworld.dogdog.rating.interfaces.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class RatingRequest {

    // TODO 로그인 기능이 아직 없어서 임시로 memberId 받아오도록 함
    @NotNull
    private Long memberId;

    @Positive @NotNull
    @Min(value = 1)
    @Max(value = 5)
    private int score;

    @Size(max = 1000)
    private String comment;
}
