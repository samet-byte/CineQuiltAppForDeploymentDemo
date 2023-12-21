package com.sametb.cinequiltapp.tv_show;

import java.util.List;
import java.util.Optional;

public interface ITVShowService {
    TVShow save(TVShow TVShow);

    List<TVShow> getAllTVShows();

    List<TVShow> getAllTVShowsByMetadataId(Integer id);

    List<TVShow> getByMetadataIdAndSeason(Integer metadataId, Integer season);

    void deleteTVShowById(Integer id);

    boolean updateEpisode(Integer id, TVShowRequest tvShowRequest);

    TVShow findByMetadataIdAndSeasonAndEpisode(Integer metadataId, Integer season, Integer episode);

    Optional<TVShow> getTVShowById(Integer id);

    void deleteAllByMetadataId(Integer metadataId);
}
