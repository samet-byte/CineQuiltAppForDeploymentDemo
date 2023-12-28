package com.sametb.cinequiltapp.episode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Samet Bayat.
 * Date: 12.12.2023 1:56 PM
 * Project Name: CineQuiltApp
 * ©2023, NONE OF THE RIGHTS RESERVED.
 * MAYBE SOME OF 'EM. WHO KNOWS?
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EpisodeResponse {

    private Integer metadataId;
    private String metadataTitle;

    private Integer id;
    private Integer season;
    private Integer episode;
    private String title;
    private String videoUrl;
    private String description;

    public static EpisodeResponse fromTVShow(@NotNull Episode Episode) {
        return EpisodeResponse.builder()
                .metadataId(Episode.getSeries().getId())
                .metadataTitle(Episode.getSeries().getTitle())
                .id(Episode.getId())
                .season(Episode.getSeason())
                .episode(Episode.getEpisode())
                .videoUrl(Episode.getVideoUrl())
                .description(Episode.getDescription())
                .title(Episode.getTitle())
                .build();
    }

    @NotNull
    public static List<EpisodeResponse> fromTVShowList(@NotNull List<Episode> Episodes) {
        List<EpisodeResponse> responseList = new ArrayList<>();
        for (Episode episode : Episodes) { responseList.add(fromTVShow(episode)); }
        return responseList;
    }

}


