package com.nalaka.goviya.repository;

import com.nalaka.goviya.model.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends MongoRepository<Rating, String> {

    List<Rating> findByRevieweeId(String revieweeId);

    List<Rating> findByReviewerId(String reviewerId);

    Optional<Rating> findByBidId(String bidId);

    boolean existsByBidIdAndReviewerId(String bidId, String reviewerId);
}
