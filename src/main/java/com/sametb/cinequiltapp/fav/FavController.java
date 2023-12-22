package com.sametb.cinequiltapp.fav;

import com.sametb.cinequiltapp.metadata.MetadataService;
import com.sametb.cinequiltapp.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sametb.cinequiltapp.fav.FavBuilder.*;

/**
 * @author Samet Bayat.
 * Date: 9.12.2023 3:53 PM
 * Project Name: CineQuiltApp
 * ©2023, NONE OF THE RIGHTS RESERVED.
 * MAYBE SOME OF 'EM. WHO KNOWS?
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/favs")
@CrossOrigin("http://localhost:3000")
public class FavController {

        private final IFavService favouriteService;

        private final IUserService userService;

        private final MetadataService metadataService;


    @PostMapping
    public ResponseEntity<FavResponse> saveFavourite(@NotNull @RequestBody FavRequest favRequest) {
        if (favouriteService.getByUserIdAndMetadataId(favRequest.getUserId(), favRequest.getMetadataId()) != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Favourite favourite = buildFavWithRequest(favRequest, userService, metadataService);
        favouriteService.saveFavourite(favourite);
        FavResponse favResponse = fromFavorite(favourite);
        return new ResponseEntity<>(favResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<FavResponse>> getAllFavourites() {
        List<Favourite> favourites = favouriteService.getAllFavourites();
        if (favourites.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<FavResponse> favResponses = fromFavourites(favourites);
        return new ResponseEntity<>(favResponses, HttpStatus.OK);
    }

    @GetMapping("id/{id}")
    public ResponseEntity<FavResponse> getFavouriteById(@PathVariable Long id) {
        Favourite favourite = favouriteService.getFavouriteById(id);
        if (favourite == null) { return new ResponseEntity<>(HttpStatus.NOT_FOUND); }

        return new ResponseEntity<>(
                fromFavorite(favourite),
                HttpStatus.OK
        );
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<FavResponse>> getFavouritesByUserId(@PathVariable Integer userId) {
        List<Favourite> favourites = favouriteService.getAllByUserId(userId);
        if (favourites.isEmpty()) { return new ResponseEntity<>(HttpStatus.NOT_FOUND); }
        List<FavResponse> favResponses = fromFavourites(favourites);
        return new ResponseEntity<>(favResponses, HttpStatus.OK);
    }

    @DeleteMapping("id/{id}")
    public ResponseEntity<Void> deleteFavourite(@PathVariable Long id) {
        favouriteService.deleteFavourite(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{metadataId}")
    public ResponseEntity<Void> deleteFavouriteByMetadataId(@PathVariable Integer metadataId) {
        favouriteService.deleteFavouriteByMetadataId(metadataId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*@PostMapping("/isFaved")
    public ResponseEntity<IsFaved> isFaved(
            @RequestBody FavRequest favRequest,
            @RequestParam(required = false) boolean changeState
    ) {
        Favourite favourite = favouriteService.getByUserIdAndMetadataId(favRequest.getUserId(), favRequest.getMetadataId());

        boolean shouldChangeState = false || changeState;
        boolean isFavedResult = false;

        if(favourite == null) {
            if (shouldChangeState) {
                favouriteService.saveFavourite(buildFavWithRequest(favRequest, userService, metadataService));
                isFavedResult = true;
            }
        } else {
            if (shouldChangeState) {
                favouriteService.deleteFavourite(favourite.getId());
            } else {
                isFavedResult = true;
            }
        }
        IsFaved isFaved =  new IsFaved(isFavedResult);
        return new ResponseEntity<>(isFaved, HttpStatus.OK);
    }*/

    @PostMapping("/isFaved")
    public ResponseEntity<Map<String, Object>> isFaved(
            @RequestBody FavRequest favRequest,
            @RequestParam(required = false) boolean changeState
    ) {
        Favourite favourite = favouriteService.getByUserIdAndMetadataId(favRequest.getUserId(), favRequest.getMetadataId());

        boolean shouldChangeState = false || changeState;
        boolean isFavedResult = false;

        if (favourite == null) {
            if (shouldChangeState) {
                favouriteService.saveFavourite(buildFavWithRequest(favRequest, userService, metadataService));
                isFavedResult = true;
            }
        } else {
            if (shouldChangeState) {
                favouriteService.deleteFavourite(favourite.getId());
            } else {
                isFavedResult = true;
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("faved", isFavedResult);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }




}
