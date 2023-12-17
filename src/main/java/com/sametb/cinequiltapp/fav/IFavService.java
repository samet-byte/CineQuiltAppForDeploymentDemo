package com.sametb.cinequiltapp.fav;

import java.util.List;

public interface IFavService {

    void saveFavourite(Favourite favourite);
    List<Favourite> getAllFavourites();
    Favourite getFavouriteById(Long id);

    void deleteFavourite(Long id);

    List<Favourite> getAllByUserId(Integer userId);

    void deleteFavouriteByMetadataId(Integer metadataId);

    Favourite getByUserIdAndMetadataId(Integer userId, Integer metadataId);
}
