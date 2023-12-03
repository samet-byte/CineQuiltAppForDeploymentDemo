package com.sametb.cinequiltapp.favs;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FavouriteRepository extends JpaRepository<Favourite, Integer> {

    Favourite findById(int id);

    @Query("SELECT f FROM Favourite f LEFT JOIN FETCH f.user LEFT JOIN FETCH f.metadata")
    List<Favourite> findAllWithUserAndMetadata();

    List<Favourite> findByUserId(Integer userId);


    @Transactional
    @Modifying
    @Query("DELETE FROM Favourite f WHERE f.id = :id")
    void deleteByIdCustom(Integer id);


}

