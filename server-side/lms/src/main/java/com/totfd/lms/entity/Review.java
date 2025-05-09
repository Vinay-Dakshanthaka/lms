package com.totfd.lms.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private Users users;

    @Column(name = "target_type", nullable = false)
    private String targetType; // e.g., "package", "skill", etc.

    @Column(name = "target_id", nullable = false)
    private Long targetId;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "presentation_rating")
    private Integer presentationRating; // Only for video presentation

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
