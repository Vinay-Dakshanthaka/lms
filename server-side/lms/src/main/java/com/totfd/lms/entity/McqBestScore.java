package com.totfd.lms.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "mcq_best_scores")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class McqBestScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private Users users;

    @ManyToOne(optional = false)
    @JoinColumn(name = "sub_topic_id", nullable = false)
    private SubTopic subTopic;

    @Column(name = "best_score")
    private Integer bestScore;

    @Column(name = "total_marks")
    private Integer totalMarks;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;
}

