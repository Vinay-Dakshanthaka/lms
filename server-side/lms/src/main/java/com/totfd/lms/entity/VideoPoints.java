package com.totfd.lms.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "video_points")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VideoPoints {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users users;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id", nullable = false)
    private Video video;

    private Integer points;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
