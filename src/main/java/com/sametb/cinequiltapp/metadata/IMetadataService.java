package com.sametb.cinequiltapp.metadata;

import java.util.List;
import java.util.Optional;

public interface IMetadataService {

    Metadata save(MetadataRequest request);

    List<Metadata> findAll();

    void deleteMetadata(Integer id);

    Metadata findById(Integer id);

    Metadata findByTitle(String title);

    List<Metadata> findByTitleContaining(String title);

    List<Metadata> findByDirectorContaining(String director);

    List<Metadata> findByReleaseYear(int releaseYear);

    List<Metadata> findByDuration(int duration);

    List<Metadata> findByTitleContainingOrDirectorContaining(String title, String director);

    List<Metadata> findByTitleContainingOrDirectorContainingOrYearContaining(String query);

    Optional<Metadata> updateMetadataById(Integer id, Metadata updatedMetadata);
}
