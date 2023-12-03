package com.sametb.cinequiltapp.metadata;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MetadataRepository extends JpaRepository<Metadata, Integer> {

//    @Modifying

    @Override
    Optional<Metadata> findById(Integer integer);

    Optional<Metadata> findByTitleIgnoreCase(String title);

    List<Metadata> findByTitleIgnoreCaseContaining(String substring);

    List<Metadata> findByDirectorIgnoreCaseContaining(String substring);

    List<Metadata> findByReleaseYear(int releaseYear);

    List<Metadata> findByDuration(int duration);

    //for search
    List<Metadata> findByTitleContainingIgnoreCaseOrDirectorContainingIgnoreCase(String title, String director);

    @Query(value = "SELECT m FROM Metadata m WHERE LOWER(m.title) LIKE LOWER(:query) OR LOWER(m.director) LIKE LOWER(:query) OR CAST(m.releaseYear AS STRING) LIKE LOWER(:query)")
    List<Metadata> findByTitleContainingOrDirectorContainingOrYearContainingExact(@Param("query") String query);

    @Query(value = "SELECT m FROM Metadata m WHERE LOWER(m.title) LIKE LOWER(concat('%', :query, '%')) OR LOWER(m.director) LIKE LOWER(concat('%', :query, '%')) OR LOWER(CAST(m.releaseYear AS STRING)) LIKE LOWER(concat('%', :query, '%'))")
    List<Metadata> findByTitleContainingOrDirectorContainingOrYearContaining(@Param("query") String query);


    Optional<Metadata> findByTitle(String title);




//    Optional<Metadata> findByGenre(String genre);
//    Optional<Metadata> findByRating(String rating);


}
