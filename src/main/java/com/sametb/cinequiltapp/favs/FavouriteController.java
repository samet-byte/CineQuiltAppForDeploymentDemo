package com.sametb.cinequiltapp.favs;

import com.sametb.cinequiltapp._custom.SamTextFormat;
import com.sametb.cinequiltapp.metadata.Metadata;
import com.sametb.cinequiltapp.metadata.MetadataService;
import com.sametb.cinequiltapp.user.User;
import com.sametb.cinequiltapp.user.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author Samet Bayat.
 * Date: 3.12.2023 5:11 PM
 * Project Name: CineQuiltApp
 * ©2023, NONE OF THE RIGHTS RESERVED.
 * MAYBE SOME OF 'EM. WHO KNOWS?
 */

@RestController
@RequestMapping("/api/v1/fav")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000")
public class FavouriteController {

    private final FavouriteService favouriteService;

    private final MetadataService metadataService;

    private final UserService userService;

    @PostMapping
    public ResponseEntity<FavouriteDTO> createFavourite(@RequestBody FavouriteRequest favouriteRequest) {

        Metadata metadata = metadataService.findById(favouriteRequest.getMetadataId());
        User user =  userService.findByIDNonOptional(favouriteRequest.getUserId());

//        SamTextFormat.Companion.create("Metadata: " + metadata.getTitle()).magenta().print();
//        SamTextFormat.Companion.create("Metadata: " + user.getUsername()).magenta().print();

        Favourite favourite = Favourite.builder()
                .metadata(metadata)
                .user(user)
                .build();
        Favourite savedFavourite = favouriteService.saveFavourite(favourite);
//        SamTextFormat.Companion.create("savedFavourite: " + savedFavourite.getUser().getEmail()).magenta().print();
        return ResponseEntity.ok(FavouriteMapper.INSTANCE.favouriteToDto(savedFavourite));
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<FavouriteDTO> getFavouriteById(@PathVariable Integer id) {
////        Optional<Favourite> favourite = favouriteService.getFavouriteById(id);
////        return favourite.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
//        Favourite favourite = favouriteService.getFavouriteByIdNonOptional(id);
//        return ResponseEntity.ok(FavouriteMapper.INSTANCE.favouriteToDto(favourite));
//    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<FavouriteDTO>> getFavouriteByUserId(@PathVariable Integer userId) {
        List<Favourite> favourites = favouriteService.findByUserId(userId);
        return ResponseEntity.ok(FavouriteMapper.INSTANCE.favouritesToDTOs(favourites));
    }

    @GetMapping
    public ResponseEntity<List<FavouriteDTO>> getAllFavourites() {
        List<Favourite> favourites = favouriteService.getAllFavourites();
        return ResponseEntity.ok(FavouriteMapper.INSTANCE.favouritesToDTOs(favourites));
    }

    //todo: delete by user id and metadata id
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFavourite(@PathVariable Integer id) {
        try {
            favouriteService.deleteFavourite(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }
}

