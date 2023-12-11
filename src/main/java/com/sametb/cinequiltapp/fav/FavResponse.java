package com.sametb.cinequiltapp.fav;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Samet Bayat.
 * Date: 9.12.2023 3:59 PM
 * Project Name: CineQuiltApp
 * ©2023, NONE OF THE RIGHTS RESERVED.
 * MAYBE SOME OF 'EM. WHO KNOWS?
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FavResponse {

    private Integer userId;
//    private String user;
    private Integer metadataId;
    private String title;
    private String posterUrl;



    public static FavResponse fromFavorite(@NotNull Favourite favourite) {
        return FavResponse.builder()
//                .user(favourite.getUser().getUsername())
                .userId(favourite.getUser().getId())
                .metadataId(favourite.getMetadata().getId())
                .title(favourite.getMetadata().getTitle())
                .posterUrl(favourite.getMetadata().getPosterUrl())
                .build();
    }


    @NotNull
    public static List<FavResponse> fromFavourites(@NotNull List<Favourite> favourites) {
        List<FavResponse> favResponses = new ArrayList<>();

        for (Favourite favourite : favourites) {
            favResponses.add(fromFavorite(favourite));
        }
        return favResponses;
    }


}
