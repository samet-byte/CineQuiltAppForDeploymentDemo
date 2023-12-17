package com.sametb.cinequiltapp.tv_show;

import com.sametb.cinequiltapp._custom.SamTextFormat;
import com.sametb.cinequiltapp.metadata.MetadataService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author Samet Bayat.
 * Date: 12.12.2023 1:52 PM
 * Project Name: CineQuiltApp
 * ©2023, NONE OF THE RIGHTS RESERVED.
 * MAYBE SOME OF 'EM. WHO KNOWS?
 */


@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/v1/tvshow")
@RequiredArgsConstructor
public class TVShowController {

    private final ITVShowService tvShowService; // todo: I .. Service

    private final MetadataService metadataService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public TVShowResponse saveTVShow(@RequestBody TVShowRequest tvShowRequest) {
        TVShow tvShow = TVShow.fromTVShowRequest(tvShowRequest, metadataService.findById(tvShowRequest.getMetadataId()));
        TVShow savedTVShow = tvShowService.save(tvShow);
        return TVShowResponse.fromTVShow(savedTVShow);
    }

    @GetMapping
    public List<TVShow> getAllTVShows()  {
        return tvShowService.getAllTVShows();
    }


    @GetMapping("/{id}")
    public List<TVShow> getAllTVShowsByMetadataId(@PathVariable Integer id)  {
        return tvShowService.getAllTVShowsByMetadataId(id);
    }


    @GetMapping("/episode/{id}")
    public Optional<TVShow> getTVShowById(@PathVariable Integer id)  {
        return tvShowService.getTVShowById(id);
    }

    @Deprecated
    @GetMapping("/{metadataId}/{index}")
    public TVShow getTVShowByMetadataIdAndTVShowId(@PathVariable Integer metadataId, @PathVariable Integer index)  {
        return tvShowService.getAllTVShowsByMetadataId(metadataId).get(index);
    }

    @GetMapping("season/{metadataId}/{season}")
    public List<TVShow> getTVShowByMetadataIdAndSeason(@PathVariable Integer metadataId, @PathVariable Integer season)  {
        return tvShowService.getByMetadataIdAndSeason(metadataId, season);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEpisode(@PathVariable Integer id) {
        try{
            tvShowService.deleteTVShowById(id);
            return ResponseEntity.noContent().build();

        } catch (Exception e){
            SamTextFormat.Companion.create(e.getMessage()).red().print();
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEpisode(@NonNull @PathVariable Integer id, @RequestBody TVShowRequest tvShowRequest){
        try{
            boolean isUpdated = tvShowService.updateEpisode(id, tvShowRequest);

            if (isUpdated)
                return ResponseEntity.ok().build();
        } catch (Exception e){
            SamTextFormat.Companion.errorMessage(e.getMessage());
        }
            return ResponseEntity.badRequest().build();
    }

    @GetMapping("{metadataId}/{season}/{episode}")
    public TVShow getTVShowByMetadataIdAndSeasonAndEpisode(
            @PathVariable Integer metadataId,
            @PathVariable Integer season,
            @PathVariable Integer episode
    )  {
        return tvShowService.findByMetadataIdAndSeasonAndEpisode(metadataId, season, episode);
    }
}
