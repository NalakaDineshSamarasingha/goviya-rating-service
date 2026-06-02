package com.nalaka.goviya.service;

import com.nalaka.goviya.model.Rating;
import com.nalaka.goviya.model.dto.RatingRequestDTO;
import com.nalaka.goviya.model.dto.RatingResponseDTO;
import com.nalaka.goviya.repository.RatingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;

    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    public RatingResponseDTO createRating(RatingRequestDTO request) {
        // Prevent duplicate reviews for the same transaction/bid from the same user
        if (ratingRepository.existsByBidIdAndReviewerId(request.getBidId(), request.getReviewerId())) {
            throw new RuntimeException("You have already reviewed this transaction.");
        }

        Rating rating = new Rating();
        rating.setRatingId("RAT" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        rating.setBidId(request.getBidId());
        rating.setReviewerId(request.getReviewerId());
        rating.setRevieweeId(request.getRevieweeId());
        rating.setRating(request.getRating());
        rating.setComment(request.getComment());
        rating.setCreatedAt(LocalDateTime.now());

        Rating saved = ratingRepository.save(rating);
        return convertToResponseDTO(saved);
    }

    public List<RatingResponseDTO> getReviewsForUser(String userId) {
        return ratingRepository.findByRevieweeId(userId).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public List<RatingResponseDTO> getReviewsByUser(String userId) {
        return ratingRepository.findByReviewerId(userId).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public Optional<RatingResponseDTO> getReviewByBid(String bidId) {
        return ratingRepository.findByBidId(bidId)
                .map(this::convertToResponseDTO);
    }

    public Map<String, Object> calculateAverageRating(String userId) {
        List<Rating> ratings = ratingRepository.findByRevieweeId(userId);
        
        double average = 0.0;
        int count = ratings.size();
        
        if (count > 0) {
            double total = ratings.stream().mapToInt(Rating::getRating).sum();
            average = Math.round((total / count) * 10.0) / 10.0;
        }

        Map<String, Object> stats = new HashMap<>();
        stats.put("userId", userId);
        stats.put("averageRating", average);
        stats.put("totalReviews", count);
        return stats;
    }

    private RatingResponseDTO convertToResponseDTO(Rating rating) {
        return new RatingResponseDTO(
                rating.getRatingId(),
                rating.getBidId(),
                rating.getReviewerId(),
                rating.getRevieweeId(),
                rating.getRating(),
                rating.getComment(),
                rating.getCreatedAt()
        );
    }
}
