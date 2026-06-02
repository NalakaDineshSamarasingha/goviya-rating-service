package com.nalaka.goviya.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "ratings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rating {

    @Id
    private String id;

    @Indexed(unique = true)
    private String ratingId;

    @Indexed
    private String bidId;

    @Indexed
    private String reviewerId;

    @Indexed
    private String revieweeId;

    private Integer rating;

    private String comment;

    private LocalDateTime createdAt;
}
