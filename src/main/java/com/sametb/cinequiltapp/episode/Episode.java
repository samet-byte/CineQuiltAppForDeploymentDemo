package com.sametb.cinequiltapp.episode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sametb.cinequiltapp.series.Series;
import jakarta.persistence.*;
import lombok.*;
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
@Table(name = "episodes")
public class Episode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "series_id", referencedColumnName = "id")
    private Series series;

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



    public static Episode fromTVShowRequest(@NotNull EpisodeRequest episodeRequest, Series series) {
        return Episode.builder()
                .series(series)
                .season(episodeRequest.getSeason())
                .episode(episodeRequest.getEpisode())
                .title(episodeRequest.getTitle())
                .videoUrl(episodeRequest.getVideoUrl())
                .description(episodeRequest.getDescription())
                .build();
    }


    public static boolean areSame(EpisodeRequest request, Episode episode) {
        if (request == null || episode == null) { return true; }
        boolean titlesEqual = Objects.equals(request.getTitle(), episode.getTitle());
        boolean descriptionsEqual = Objects.equals(request.getDescription(), episode.getDescription());
        boolean seasonsEqual = Objects.equals(request.getSeason(), episode.getSeason());
        boolean episodesEqual = Objects.equals(request.getEpisode(), episode.getEpisode());
        boolean videoUrlsEqual = Objects.equals(request.getVideoUrl(), episode.getVideoUrl());

        return titlesEqual && descriptionsEqual && seasonsEqual && episodesEqual && videoUrlsEqual;
    }

}
