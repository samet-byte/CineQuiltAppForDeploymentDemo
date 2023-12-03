package com.sametb.cinequiltapp.metadata;

import com.sametb.cinequiltapp.exception.MetadataAlreadyExistsException;
import com.sametb.cinequiltapp.exception.MetadataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
@RequiredArgsConstructor

public class MetadataService implements IMetadataService {

    private final MetadataRepository repository;

    public boolean isMetadataAlreadyExists(String title) {
        return repository.findByTitleIgnoreCase(title).isPresent();
    }

    public Metadata save(MetadataRequest request) {

        if (request.getTitle() != null && isMetadataAlreadyExists(request.getTitle())) {
            throw new MetadataAlreadyExistsException(request.getTitle() + " already exists");
        } else {

            var metadata = Metadata.builder()
                    .id(request.getId())
                    .title(request.getTitle())
                    .director(Optional.ofNullable(request.getDirector()).orElse("N/A"))
                    .releaseYear(Optional.of(request.getReleaseYear()).orElse(0))
                    .duration(Optional.of(request.getDuration()).orElse(0))
                    .posterUrl(Optional.ofNullable(request.getPosterUrl()).orElse("https://sametb.com/no-content.jpg"))
                    .videoUrl(Optional.ofNullable(request.getVideoUrl()).orElse("https://sametb.com/no-content.mp4"))
                    .trailerUrl(request.getTrailerUrl())
                    .soundtrackUrl(request.getSoundtrackUrl())
                    .build();
            repository.save(metadata);
            return metadata;
        }
    }


    public List<Metadata> findAll() {
        return repository.findAll();
    }

    public void deleteMetadata(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public Metadata findById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new MetadataNotFoundException("Metadata not found with this ID!"));
    }

    @Override
    public Metadata findByTitle(String title) {
        return repository.findByTitle(title).orElseThrow(() -> new MetadataNotFoundException("Metadata not found with this title!"));
    }

    @Override
    public List<Metadata> findByTitleContaining(String title) {
        return repository.findByTitleIgnoreCaseContaining(title);
    }

    @Override
    public List<Metadata> findByDirectorContaining(String director) {
        return repository.findByDirectorIgnoreCaseContaining(director);
    }

    @Override
    public List<Metadata> findByReleaseYear(int releaseYear) {
        return repository.findByReleaseYear(releaseYear);
    }

    @Override
    public List<Metadata> findByDuration(int duration) {
        return repository.findByDuration(duration);
    }

    @Override
    public List<Metadata> findByTitleContainingOrDirectorContaining(String title, String director) {
        return repository.findByTitleContainingIgnoreCaseOrDirectorContainingIgnoreCase(title, director);
    }

    @Override
    public List<Metadata> findByTitleContainingOrDirectorContainingOrYearContaining(String query) {
        return repository.findByTitleContainingOrDirectorContainingOrYearContaining(query);
    }


    public Optional<Metadata> updateMetadataById(Integer id, Metadata updatedMetadata) {
        if (updatedMetadata == null || id == null || repository.findById(id).isEmpty()) {
            return Optional.empty();
        }

        Metadata uMetadata = repository.findById(id).orElseThrow();

        // Use BeanUtils to copy non-null properties from updatedMetadata to uMetadata
        BeanUtils.copyProperties(updatedMetadata, uMetadata, getNullPropertyNames(updatedMetadata));

        // Check if any changes were made before saving
        boolean changesMade = !uMetadata.equals(updatedMetadata);

        if (changesMade) {
            // Save the updated entity back to the database
            return Optional.of(repository.save(uMetadata));
        } else {
            // No changes, return the existing entity
            return Optional.of(uMetadata);
        }
    }

    // Helper method to get null property names of an object
    private String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            // Check if value is null and add the property name to the set
            if (src.getPropertyValue(pd.getName()) == null) {
                emptyNames.add(pd.getName());
            }
        }

        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}





//    public Optional<Metadata> updateMetadataById(Integer id, Metadata updatedMetadata) {
//        if (updatedMetadata == null || id == null || repository.findById(id).isEmpty()) {
//            return Optional.empty();
//        }
//
//        Metadata uMetadata = repository.findById(id).orElseThrow();
//        if (updatedMetadata.getTitle() != null && !updatedMetadata.getTitle().equals(uMetadata.getTitle())) { uMetadata.setTitle(updatedMetadata.getTitle()); }
//        if (updatedMetadata.getDirector() != null && !updatedMetadata.getDirector().equals(uMetadata.getDirector())) { uMetadata.setDirector(updatedMetadata.getDirector()); }
//        if (updatedMetadata.getReleaseYear() != 0 && updatedMetadata.getReleaseYear() != uMetadata.getReleaseYear()) { uMetadata.setReleaseYear(updatedMetadata.getReleaseYear()); }
//        if (updatedMetadata.getDuration() != 0 && updatedMetadata.getDuration() != uMetadata.getDuration()) { uMetadata.setDuration(updatedMetadata.getDuration()); }
//        if (updatedMetadata.getPosterUrl() != null && !updatedMetadata.getPosterUrl().equals(uMetadata.getPosterUrl())) { uMetadata.setPosterUrl(updatedMetadata.getPosterUrl()); }
//        if (updatedMetadata.getVideoUrl() != null && !updatedMetadata.getVideoUrl().equals(uMetadata.getVideoUrl())) { uMetadata.setVideoUrl(updatedMetadata.getVideoUrl()); }
//        if (updatedMetadata.getTrailerUrl() != null && !updatedMetadata.getTrailerUrl().equals(uMetadata.getTrailerUrl())) { uMetadata.setTrailerUrl(updatedMetadata.getTrailerUrl()); }
//        if (updatedMetadata.getSoundtrackUrl() != null && !updatedMetadata.getSoundtrackUrl().equals(uMetadata.getSoundtrackUrl())) { uMetadata.setSoundtrackUrl(updatedMetadata.getSoundtrackUrl()); }
//
//        repository.findById(id).map(existingMetadata -> {
////            if (
////                    if (updatedMetadata.getTitle() != null && !updatedMetadata.getTitle().equals(existingMetadata.getTitle()))
////
////                    (updatedMetadata.getDirector() != null && !updatedMetadata.getDirector().equals(existingMetadata.getDirector()))
////                    ||
////                    (updatedMetadata.getReleaseYear() != 0 && updatedMetadata.getReleaseYear() != existingMetadata.getReleaseYear())
////                    ||
////                    (updatedMetadata.getDuration() != 0 && updatedMetadata.getDuration() != existingMetadata.getDuration())
////                    ||
////                    (updatedMetadata.getPosterUrl() != null && !updatedMetadata.getPosterUrl().equals(existingMetadata.getPosterUrl()))
////                    ||
////                    (updatedMetadata.getVideoUrl() != null && !updatedMetadata.getVideoUrl().equals(existingMetadata.getVideoUrl()))
////                    ||
////                    (updatedMetadata.getTrailerUrl() != null && !updatedMetadata.getTrailerUrl().equals(existingMetadata.getTrailerUrl()))
////                    ||
////                    (updatedMetadata.getSoundtrackUrl() != null && !updatedMetadata.getSoundtrackUrl().equals(existingMetadata.getSoundtrackUrl()))
////
////            )
//            {
//                existingMetadata.setTitle(updatedMetadata.getTitle());
//                existingMetadata.setDirector(updatedMetadata.getDirector());
//                // Update other fields as needed
//
//                // The save method is used to save the updated entity back to the database
//                return repository.save(existingMetadata);
//            } else {
//                return existingMetadata;
//            }
//        });
//    }
