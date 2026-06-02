package com.nalaka.goviya.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingRequestDTO {

    @NotBlank(message = "Bid ID is required")
    private String bidId;

    @NotBlank(message = "Reviewer ID is required")
    private String reviewerId;

    @NotBlank(message = "Reviewee ID is required")
    private String revieweeId;

    @NotNull(message = "Rating value is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must not exceed 5")
    private Integer rating;

    private String comment;
}
