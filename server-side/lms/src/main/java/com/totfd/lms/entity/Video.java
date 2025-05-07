package com.totfd.lms.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "videos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_topic_id", nullable = false)
    private SubTopic subTopic;

    private String url;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    private Integer duration; // in seconds

    private Integer version;

    @Column(name = "is_latest")
    private Boolean isLatest;

    @Column(name = "no_of_views")
    private Long noOfViews;
}
