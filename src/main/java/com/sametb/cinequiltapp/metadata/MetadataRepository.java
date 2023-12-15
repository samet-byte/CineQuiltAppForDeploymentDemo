package com.sametb.cinequiltapp.metadata;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Meta;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MetadataRepository extends JpaRepository<Metadata, Integer> {

//    @Modifying

    Optional<Metadata> findById(Integer id);

    Optional<Metadata> getMetadataById(Integer id);

    void deleteById(@NonNull Integer id);

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


    //Expecting a SELECT query : `DELETE FROM Metadata m WHERE m.id = :id`

    @Query("DELETE FROM Metadata m WHERE m.id = :id")
    void customDelete(Integer id);


    void deleteByTitle(String title);


//    Optional<Metadata> findByGenre(String genre);
//    Optional<Metadata> findByRating(String rating);


    // SORTING
    @Query("SELECT m FROM Metadata m ORDER BY m.title ASC, m.director ASC, m.releaseYear ASC, m.duration ASC")
    List<Metadata> findAllOrderByTitleAsc();

    @Query("SELECT m FROM Metadata m ORDER BY m.title DESC, m.director ASC, m.releaseYear ASC, m.duration ASC")
    List<Metadata> findAllOrderByTitleDesc();

    @Query("SELECT m FROM Metadata m ORDER BY m.director ASC, m.title ASC, m.releaseYear ASC, m.duration ASC")
    List<Metadata> findAllOrderByDirectorAsc();
    @Query("SELECT m FROM Metadata m ORDER BY m.director DESC, m.title ASC, m.releaseYear ASC, m.duration ASC")
    List<Metadata> findAllOrderByDirectorDesc();

    @Query("SELECT m FROM Metadata m ORDER BY m.releaseYear ASC, m.title ASC, m.director ASC, m.duration ASC")
    List<Metadata> findAllOrderByYearAsc();
    @Query("SELECT m FROM Metadata m ORDER BY m.releaseYear DESC, m.title ASC, m.director ASC, m.duration ASC")
    List<Metadata> findAllOrderByYearDesc();

    @Query("SELECT m FROM Metadata m ORDER BY m.duration ASC, m.title ASC, m.director ASC, m.releaseYear ASC")
    List<Metadata> findAllOrderByDurationAsc();
    @Query("SELECT m FROM Metadata m ORDER BY m.duration DESC, m.title ASC, m.director ASC, m.releaseYear ASC")
    List<Metadata> findAllOrderByDurationDesc();


}
