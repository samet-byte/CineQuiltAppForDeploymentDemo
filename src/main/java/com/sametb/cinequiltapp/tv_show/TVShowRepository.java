package com.sametb.cinequiltapp.tv_show;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * @author Samet Bayat.
 * Date: 12.12.2023 1:51 PM
 * Project Name: CineQuiltApp
 * ©2023, NONE OF THE RIGHTS RESERVED.
 * MAYBE SOME OF 'EM. WHO KNOWS?
 */

public interface TVShowRepository extends JpaRepository<TVShow, Integer> {



    List<TVShow> findAllByMetadataId(Integer id);

    @Query("SELECT tv FROM TVShow tv GROUP BY tv, tv.metadata.id")
    List<Object[]> getAllTVShowsGroupByTitle();


    @Query("SELECT new com.sametb.cinequiltapp.tv_show.TVShowResponse(tv.metadata.id, tv.metadata.title, tv.id, tv. season, tv.episode, tv.title, tv.videoUrl, tv.description) FROM TVShow tv WHERE tv.metadata.id = :metadataId GROUP BY tv, tv.metadata.id, tv.metadata.title, tv.season")
    List<Object []> getTVShowsGroupBySeasonWhereMetadataId(Integer metadataId);


//    List<TVShow> findAllByMetadataIdAndSeason(Integer metadataId, Integer season);

    List<TVShow> findAllByMetadataIdAndSeasonOrderByEpisode(Integer metadataId, Integer season);

    TVShow findByMetadataIdAndSeasonAndEpisode(Integer metadataId, Integer season, Integer episode);

    @Query("SELECT DISTINCT MAX(tv.season) FROM TVShow tv WHERE tv.metadata.id = :metadataId")
    Integer howManySeasons(Integer metadataId);

}
