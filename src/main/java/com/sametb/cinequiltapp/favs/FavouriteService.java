package com.sametb.cinequiltapp.favs;

/**
 * @author Samet Bayat.
 * Date: 3.12.2023 5:10 PM
 * Project Name: CineQuiltApp
 * ©2023, NONE OF THE RIGHTS RESERVED.
 * MAYBE SOME OF 'EM. WHO KNOWS?
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class FavouriteService implements IFavouriteService {

    @Autowired
    private FavouriteRepository favouriteRepository;

    @Transactional
    public Favourite saveFavourite(Favourite favourite) {
        return favouriteRepository.save(favourite);
    }

    public Optional<Favourite> getFavouriteById(Integer id) {
        return favouriteRepository.findById(id);
    }
    public Favourite getFavouriteByIdNonOptional(Integer id) {
        return favouriteRepository.findById(id).get();
    }

    public List<Favourite> getAllFavourites() {
        return favouriteRepository.findAllWithUserAndMetadata();
//        return favouriteRepository.findAll();
    }

    @Transactional
    public void deleteFavourite(Integer id) {
        favouriteRepository.deleteByIdCustom(id);
    }


    public List<Favourite> findByUserId(Integer userId) {
        return favouriteRepository.findByUserId(userId);
    }
}

