package com.totfd.lms.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "mcq_progress")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class McqProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private Users users;

    @ManyToOne(optional = false)
    @JoinColumn(name = "mcq_id", nullable = false)
    private Mcq mcq;

    @Column(nullable = false)
    private String status; // "started" or "completed"

    @Column(name = "last_accessed_at")
    private LocalDateTime lastAccessedAt;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
