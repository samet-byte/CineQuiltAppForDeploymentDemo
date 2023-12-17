package com.sametb.cinequiltapp.tv_show;

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
public class TVShowResponse {

    private Integer metadataId;
    private String metadataTitle;

    private Integer id;
    private Integer season;
    private Integer episode;
    private String title;
    private String videoUrl;
    private String description;

    public static TVShowResponse fromTVShow(@NotNull TVShow TVShow) {
        return TVShowResponse.builder()
                .metadataId(TVShow.getMetadata().getId())
                .metadataTitle(TVShow.getMetadata().getTitle())
                .id(TVShow.getId())
                .season(TVShow.getSeason())
                .episode(TVShow.getEpisode())
                .videoUrl(TVShow.getVideoUrl())
                .description(TVShow.getDescription())
                .title(TVShow.getTitle())
                .build();
    }

    @NotNull
    public static List<TVShowResponse> fromTVShowList(@NotNull List<TVShow> TVShows) {
        List<TVShowResponse> responseList = new ArrayList<>();
        for (TVShow tvShow : TVShows) { responseList.add(fromTVShow(tvShow)); }
        return responseList;
    }

}


