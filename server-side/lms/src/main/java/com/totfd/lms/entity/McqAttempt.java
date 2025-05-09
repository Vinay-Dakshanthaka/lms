package com.totfd.lms.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "mcq_attempts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class McqAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private Users users;

    @ManyToOne(optional = false)
    @JoinColumn(name = "mcq_id", nullable = false)
    private Mcq mcq;

    @ManyToOne(optional = false)
    @JoinColumn(name = "sub_topic_id", nullable = false)
    private SubTopic subTopic;

    @Column(name = "attempt_number", nullable = false)
    private Integer attemptNumber;

    @Column(nullable = false)
    private Integer score;

    @Column(name = "total_marks", nullable = false)
    private Integer totalMarks;

    @Column(name = "attempted_at")
    private LocalDateTime attemptedAt;
}
