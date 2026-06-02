package com.nalaka.goviya.controller;

import com.nalaka.goviya.model.dto.ApiResponseDTO;
import com.nalaka.goviya.model.dto.RatingRequestDTO;
import com.nalaka.goviya.model.dto.RatingResponseDTO;
import com.nalaka.goviya.service.RatingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/ratings")
@Tag(name = "Rating & Review Management", description = "APIs for user ratings and reviews")
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping
    @Operation(summary = "Submit a new rating & review")
    public ResponseEntity<ApiResponseDTO<RatingResponseDTO>> submitRating(
            @Valid @RequestBody RatingRequestDTO request) {
        try {
            RatingResponseDTO response = ratingService.createRating(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponseDTO<>(true, "Review submitted successfully", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponseDTO<>(false, e.getMessage(), null));
        }
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get reviews for a user (as reviewee)")
    public ResponseEntity<ApiResponseDTO<List<RatingResponseDTO>>> getReviewsForUser(
            @PathVariable String userId) {
        try {
            List<RatingResponseDTO> reviews = ratingService.getReviewsForUser(userId);
            return ResponseEntity.ok(new ApiResponseDTO<>(true, "Reviews retrieved successfully", reviews));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponseDTO<>(false, "Error retrieving reviews: " + e.getMessage(), null));
        }
    }

    @GetMapping("/reviewer/{reviewerId}")
    @Operation(summary = "Get reviews written by a user")
    public ResponseEntity<ApiResponseDTO<List<RatingResponseDTO>>> getReviewsByUser(
            @PathVariable String reviewerId) {
        try {
            List<RatingResponseDTO> reviews = ratingService.getReviewsByUser(reviewerId);
            return ResponseEntity.ok(new ApiResponseDTO<>(true, "Reviews retrieved successfully", reviews));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponseDTO<>(false, "Error retrieving reviews: " + e.getMessage(), null));
        }
    }

    @GetMapping("/bid/{bidId}")
    @Operation(summary = "Get review details for a specific transaction/bid")
    public ResponseEntity<ApiResponseDTO<RatingResponseDTO>> getReviewByBid(
            @PathVariable String bidId) {
        try {
            Optional<RatingResponseDTO> review = ratingService.getReviewByBid(bidId);
            if (review.isPresent()) {
                return ResponseEntity.ok(new ApiResponseDTO<>(true, "Review found", review.get()));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponseDTO<>(false, "No review found for this transaction", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponseDTO<>(false, "Error retrieving review: " + e.getMessage(), null));
        }
    }

    @GetMapping("/user/{userId}/average")
    @Operation(summary = "Get average rating stats for a user")
    public ResponseEntity<ApiResponseDTO<Map<String, Object>>> getAverageRating(
            @PathVariable String userId) {
        try {
            Map<String, Object> stats = ratingService.calculateAverageRating(userId);
            return ResponseEntity.ok(new ApiResponseDTO<>(true, "Average stats retrieved successfully", stats));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponseDTO<>(false, "Error retrieving stats: " + e.getMessage(), null));
        }
    }
}
