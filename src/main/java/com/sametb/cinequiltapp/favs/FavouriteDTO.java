package com.sametb.cinequiltapp.favs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Samet Bayat.
 * Date: 3.12.2023 7:20 PM
 * Project Name: CineQuiltApp
 * ©2023, NONE OF THE RIGHTS RESERVED.
 * MAYBE SOME OF 'EM. WHO KNOWS?
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavouriteDTO {

    private Integer id;
    private Integer userId;
    private Integer metadataId;
    private String metadataTitle;

    public static FavouriteDTO fromFavourite(Favourite favourite) {
        FavouriteDTO favouriteDTO = new FavouriteDTO();
        favouriteDTO.setId(favourite.getId());
        favouriteDTO.setUserId(favourite.getUser().getId());
        favouriteDTO.setMetadataId(favourite.getMetadata().getId());
        favouriteDTO.setMetadataTitle(favourite.getMetadata().getTitle());
        return favouriteDTO;
    }

    public static List<FavouriteDTO> fromFavourites(List<Favourite> favourites) {
        return favourites.stream().map(FavouriteDTO::fromFavourite).toList();
    }
}
