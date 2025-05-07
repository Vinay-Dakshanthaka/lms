package com.totfd.lms.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "study_materials")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudyMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_topic_id", nullable = false)
    private SubTopic subTopic;

    @Column(name = "pdf_url")
    private String pdfUrl;

    private Integer version;

    @Column(name = "is_latest")
    private Boolean isLatest;
}