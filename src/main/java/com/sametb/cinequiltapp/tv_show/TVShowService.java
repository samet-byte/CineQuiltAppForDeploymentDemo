package com.sametb.cinequiltapp.tv_show;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Samet Bayat.
 * Date: 12.12.2023 1:52 PM
 * Project Name: CineQuiltApp
 * ©2023, NONE OF THE RIGHTS RESERVED.
 * MAYBE SOME OF 'EM. WHO KNOWS?
 */

@Service
@RequiredArgsConstructor
public class TVShowService implements ITVShowService {

    private final TVShowRepository tvShowRepository;

    @Override
    public TVShow save(TVShow TVShow) {
        return tvShowRepository.save(TVShow);
    }


    public List<TVShow> getAllTVShows() {
        return tvShowRepository.findAll();
    }

//    public List<Object[]> getAllTVShowsGroupByTitle() {
//        return tvShowRepository.getAllTVShowsGroupByTitle();
//    }

    public List<TVShow> getAllTVShowsByMetadataId(Integer id) {
        return tvShowRepository.findAllByMetadataId(id);
    }

    public List<TVShow> getByMetadataIdAndSeason(Integer metadataId, Integer season) {
        return tvShowRepository.findAllByMetadataIdAndSeasonOrderByEpisode(metadataId, season);
    }

    public void deleteTVShowById(Integer id) {
        tvShowRepository.deleteById(id);
    }

    public boolean updateEpisode(Integer id, TVShowRequest tvShowRequest) {
        if (tvShowRequest == null) return false;

        TVShow uTVShow = tvShowRepository.findById(id).orElseThrow();

        if (TVShow.areSame(tvShowRequest, uTVShow)) return false;

        BeanUtils.copyProperties(tvShowRequest, uTVShow, "id", "metadata");

        tvShowRepository.save(uTVShow);
        return true;
    }

    public TVShow findByMetadataIdAndSeasonAndEpisode(Integer metadataId, Integer season, Integer episode){
        return tvShowRepository.findByMetadataIdAndSeasonAndEpisode(metadataId, season, episode);
    }


    public Optional<TVShow> getTVShowById(Integer id) {
        return tvShowRepository.findById(id);
    }
}
