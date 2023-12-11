package com.sametb.cinequiltapp.fav;

import com.sametb.cinequiltapp.metadata.MetadataService;
import com.sametb.cinequiltapp.user.UserService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Samet Bayat.
 * Date: 9.12.2023 3:53 PM
 * Project Name: CineQuiltApp
 * ©2023, NONE OF THE RIGHTS RESERVED.
 * MAYBE SOME OF 'EM. WHO KNOWS?
 */

@RestController
@RequestMapping("/api/v1/favs")
@RequiredArgsConstructor
//@CrossOrigin("*")
@CrossOrigin("http://localhost:3000")
public class FavController {

        private final IFavService favouriteService;

        private final UserService userService;

        private final MetadataService metadataService;


    @PostMapping
    public ResponseEntity<FavResponse> saveFavourite(@RequestBody FavRequest favRequest) {

        if (
                favouriteService.getByUserIdAndMetadataId(favRequest.getUserId(), favRequest.getMetadataId()) != null
//                favouriteService.getFavouriteByUserNameOrEmailAndMetadataId(favRequest.getUser(), favRequest.getMetadataId()) != null
        ) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Favourite favourite = Favourite.builder()
                .user(userService.findByIDNonOptional(favRequest.getUserId()))
//                .user(userService.findByUsernameOrEmail(favRequest.getUser()))
                .metadata(metadataService.findById(favRequest.getMetadataId()))
                .createTime(LocalDateTime.now())
                .build();



//        Favourite savedFavourite =
        favouriteService.saveFavourite(favourite);

        FavResponse favResponse = FavResponse.builder()
                .userId(favourite.getUser().getId())
//                .user(favourite.getUser().getUsername())
                .metadataId(favourite.getMetadata().getId())
                .title(favourite.getMetadata().getTitle())
                .build();

        return new ResponseEntity<>(favResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<FavResponse>> getAllFavourites() {
        List<Favourite> favourites = favouriteService.getAllFavourites();

        if (favourites.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<FavResponse> favResponses = FavResponse.fromFavourites(favourites);
        return new ResponseEntity<>(favResponses, HttpStatus.OK);
    }

    @GetMapping("id/{id}")
    public ResponseEntity<FavResponse> getFavouriteById(@PathVariable Long id) {

        Favourite favourite = favouriteService.getFavouriteById(id);

        if (favourite == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(
                FavResponse.fromFavorite(favourite),
                HttpStatus.OK
        );
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<FavResponse>> getFavouritesByUserId(@PathVariable Integer userId) {
        List<Favourite> favourites = favouriteService.getAllByUserId(userId);

//    @GetMapping("/{user}")
//    public ResponseEntity<List<FavResponse>> getFavouritesByUserId(@PathVariable String user) {
//        List<Favourite> favourites = favouriteService.getAllByUsernameOrEmail(user);

        if (favourites.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<FavResponse> favResponses = FavResponse.fromFavourites(favourites);
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



    @PostMapping("/isFaved")
    public ResponseEntity<IsFaved> isFaved(
            @RequestBody FavRequest favRequest,
            @RequestParam(required = false) boolean changeState
    ) {
//        Favourite favourite = favouriteService.getFavouriteByUserNameOrEmailAndMetadataId(favRequest.getUser(), favRequest.getMetadataId());
        Favourite favourite = favouriteService.getByUserIdAndMetadataId(favRequest.getUserId(), favRequest.getMetadataId());

        boolean shouldChangeState = false || changeState;
        boolean isFavedResult = false;

        if(favourite == null) {
            if (shouldChangeState) {
                favouriteService.saveFavourite(buildFavourite(favRequest));
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
    }


    Favourite buildFavourite(@NotNull FavRequest favRequest) {
        return Favourite.builder()
                .user(userService.findByIDNonOptional(favRequest.getUserId()))
//                .user(userService.findByUsernameOrEmail(favRequest.getUser()))
                .metadata(metadataService.findById(favRequest.getMetadataId()))
                .createTime(LocalDateTime.now())
                .build();
    }


}
