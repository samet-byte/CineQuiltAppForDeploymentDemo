package com.sametb.cinequiltapp.fav;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Samet Bayat.
 * Date: 9.12.2023 3:47 PM
 * Project Name: CineQuiltApp
 * ©2023, NONE OF THE RIGHTS RESERVED.
 * MAYBE SOME OF 'EM. WHO KNOWS?
 */

@Service
@RequiredArgsConstructor
public class FavService implements IFavService {

    private final FavRepository favouriteRepository;

    @Override
    public void saveFavourite(Favourite favourite) {
        favouriteRepository.save(favourite);
    }

    @Override
    public List<Favourite> getAllFavourites() {
        return favouriteRepository.findAll();
    }

    @Override
    public Favourite getFavouriteById(Long id) {
        return favouriteRepository.findById(id).orElse(null);
    }

    @Override
    public List<Favourite> getAllByUserId(Integer userId) {
        return favouriteRepository.getAllByUserId(userId);
    }

    @Override
    public void deleteFavourite(Long id) {
        favouriteRepository.deleteById(id);
    }

    @Override
    public void deleteFavouriteByMetadataId(Integer metadataId) {
        favouriteRepository.deleteAllByMetadataId(metadataId);
    }

    @Override
    public Favourite getByUserIdAndMetadataId(Integer userId, Integer metadataId) {
        return favouriteRepository.getByUserIdAndMetadataId(userId, metadataId);
    }
}
