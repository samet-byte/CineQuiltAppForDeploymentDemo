package com.sametb.cinequiltapp.tv_show;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sametb.cinequiltapp.metadata.Metadata;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Samet Bayat.
 * Date: 11.12.2023 1:55 AM
 * Project Name: CineQuiltApp
 * ©2023, NONE OF THE RIGHTS RESERVED.
 * MAYBE SOME OF 'EM. WHO KNOWS?
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "tv_show")
public class TVShow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "metadata_title", referencedColumnName = "title") //??id
    @JoinColumn(name = "metadata_id", referencedColumnName = "id")
    private Metadata metadata;


    @Column(name = "season")
    private Integer season;

    @Column(name = "episode")
    private Integer episode;

    @Column(name = "episode_title")
    private String title;

    @Column(name = "video_url")
    private String videoUrl;

    @Column(name = "description", nullable = true, length = 1000)
    private String description;


    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createDate;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastModified;

    @CreatedBy
    @Column(nullable = false, updatable = false)
    private Integer createdBy;

    @LastModifiedBy
    @Column(insertable = false)
    private Integer lastModifiedBy;



    public static TVShow fromTVShowRequest(@NotNull TVShowRequest tvShowRequest, Metadata metadata) {
        return TVShow.builder()
                .metadata(metadata)
                .season(tvShowRequest.getSeason())
                .episode(tvShowRequest.getEpisode())
                .title(tvShowRequest.getTitle())
                .videoUrl(tvShowRequest.getVideoUrl())
                .description(tvShowRequest.getDescription())
                .build();
    }


    public static boolean areSame(TVShowRequest request, TVShow tvShow) {
        if (request == null || tvShow == null) { return true; }
        boolean titlesEqual = Objects.equals(request.getTitle(), tvShow.getTitle());
        boolean descriptionsEqual = Objects.equals(request.getDescription(), tvShow.getDescription());
        boolean seasonsEqual = Objects.equals(request.getSeason(), tvShow.getSeason());
        boolean episodesEqual = Objects.equals(request.getEpisode(), tvShow.getEpisode());
        boolean videoUrlsEqual = Objects.equals(request.getVideoUrl(), tvShow.getVideoUrl());

        return titlesEqual && descriptionsEqual && seasonsEqual && episodesEqual && videoUrlsEqual;
    }

}
