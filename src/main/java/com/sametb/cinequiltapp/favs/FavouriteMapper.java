package com.sametb.cinequiltapp.favs;

/**
 * @author Samet Bayat.
 * Date: 3.12.2023 7:26 PM
 * Project Name: CineQuiltApp
 * ©2023, NONE OF THE RIGHTS RESERVED.
 * MAYBE SOME OF 'EM. WHO KNOWS?
 */

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface FavouriteMapper {

    FavouriteMapper INSTANCE = Mappers.getMapper(FavouriteMapper.class);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "metadataId", source = "metadata.id")
    @Mapping(target = "metadataTitle", source = "metadata.title")
    FavouriteDTO favouriteToDto(Favourite favourite);

    List<FavouriteDTO> favouritesToDTOs(List<Favourite> favourites);
}
