package com.sametb.cinequiltapp.episode;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Samet Bayat.
 * Date: 12.12.2023 1:51 PM
 * Project Name: CineQuiltApp
 * ©2023, NONE OF THE RIGHTS RESERVED.
 * MAYBE SOME OF 'EM. WHO KNOWS?
 */

public interface EpisodeRepository extends JpaRepository<Episode, Integer> {
    List<Episode> findAllBySeriesId(Integer id);

    List<Episode> findAllBySeriesIdAndSeasonOrderByEpisode(Integer metadataId, Integer season);

    Episode findBySeriesIdAndSeasonAndEpisode(Integer metadataId, Integer season, Integer episode);

    @Transactional
    void deleteAllBySeriesId(Integer metadataId);

}
