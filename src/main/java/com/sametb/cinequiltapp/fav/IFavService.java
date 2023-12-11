package com.sametb.cinequiltapp.fav;

import java.util.List;

public interface IFavService {

    Favourite saveFavourite(Favourite favourite);
    List<Favourite> getAllFavourites();
    Favourite getFavouriteById(Long id);


//    Favourite getFavouriteByUserNameOrEmailAndMetadataId(String user, Integer metadataId);

//    List<Favourite> getFavListByUsername(String user);

    void deleteFavourite(Long id);





    List<Favourite> getAllByUserId(Integer userId);

//    List<Favourite> getAllByUsernameOrEmail(String user);

    void deleteFavouriteByMetadataId(Integer metadataId);

    Favourite getByUserIdAndMetadataId(Integer userId, Integer metadataId);

//    Favourite getByUserIdAndMetadataId(Integer userId, Integer metadataId);
//
//    Favourite getByUserUsernameOrUserEmailAndMetadataId(String user, Integer metadataId);
}
