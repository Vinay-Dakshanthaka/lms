package com.totfd.lms.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "mcqs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mcq {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_topic_id", nullable = false)
    private SubTopic subTopic;

    @Column(columnDefinition = "TEXT")
    private String question;

    @Column(columnDefinition = "json")
    private String options;  // You can alternatively use List<String> with a converter

    @Column(name = "correct_option")
    private String correctOption;

    private Integer version;

    @Column(name = "is_latest")
    private Boolean isLatest;
}
