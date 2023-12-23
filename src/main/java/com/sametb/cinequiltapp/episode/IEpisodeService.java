package com.sametb.cinequiltapp.episode;

import java.util.List;
import java.util.Optional;

public interface IEpisodeService {
    Episode save(Episode Episode);

    List<Episode> getAllEpisodes();

    List<Episode> getAllEpisodesBySeriesId(Integer id);

    List<Episode> getBySeriesIdAndSeason(Integer metadataId, Integer season);

    void deleteEpisodeById(Integer id);

    boolean updateEpisode(Integer id, EpisodeRequest episodeRequest);

    Episode findByEpisodeIdAndSeasonAndEpisode(Integer metadataId, Integer season, Integer episode);

    Optional<Episode> getEpisodeById(Integer id);

    void deleteAllBySeriesId(Integer metadataId);
}
