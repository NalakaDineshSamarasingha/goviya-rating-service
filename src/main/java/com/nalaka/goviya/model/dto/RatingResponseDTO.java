package com.nalaka.goviya.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingResponseDTO {
    private String ratingId;
    private String bidId;
    private String reviewerId;
    private String revieweeId;
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;
}
