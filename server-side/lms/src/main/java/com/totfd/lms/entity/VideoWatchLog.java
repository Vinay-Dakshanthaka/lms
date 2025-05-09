package com.totfd.lms.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "video_watch_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VideoWatchLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private Users users;

    @ManyToOne(optional = false)
    @JoinColumn(name = "video_id", nullable = false)
    private Video video;

    @Column(name = "watch_time_seconds", nullable = false)
    private Integer watchTimeSeconds = 0;

    @Column(name = "last_position_seconds", nullable = false)
    private Integer lastPositionSeconds = 0;

    @Column(name = "completed_views", nullable = false)
    private Integer completedViews = 0;

    @Column(name = "last_watched_at")
    private LocalDateTime lastWatchedAt;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}

