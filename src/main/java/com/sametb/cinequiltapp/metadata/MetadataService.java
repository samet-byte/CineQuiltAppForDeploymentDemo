package com.sametb.cinequiltapp.metadata;

import com.sametb.cinequiltapp._custom.SamTextFormat;
import com.sametb.cinequiltapp.exception.MetadataAlreadyExistsException;
import com.sametb.cinequiltapp.exception.MetadataNotFoundException;
import com.sametb.cinequiltapp.user.Role;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Metadata save(@NotNull MetadataRequest request) {

        if (request.getTitle() != null && isMetadataAlreadyExists(request.getTitle())) {
            throw new MetadataAlreadyExistsException(request.getTitle() + " already exists");
        } else {

            RelationType relationType = (request.getType() != null) ? request.getType() : RelationType.MOVIE;

            var metadata = Metadata.builder()
                    .id(request.getId())
                    .title(request.getTitle())
                    .director(Optional.ofNullable(request.getDirector()).orElse("N/A"))
                    .releaseYear(Optional.of(request.getReleaseYear()).orElse(0))
                    .duration(Optional.of(request.getDuration()).orElse(0))
                    .posterUrl(Optional.ofNullable(request.getPosterUrl()).orElse("https://sametb.com/no-content.jpg"))
//                    .videoUrl(Optional.ofNullable(request.getVideoUrl()).orElse("https://sametb.com/no-content.mp4"))
                    .videoUrl(request.getVideoUrl())
                    .trailerUrl(request.getTrailerUrl())
                    .soundtrackUrl(request.getSoundtrackUrl())
                    .description(request.getDescription())
                    .genre(request.getGenre())
                    .type(relationType)
                    .seasonNumber(request.getSeason())
                    .episodeNumber(request.getEpisode())
                    .build();
            repository.save(metadata);
            return metadata;
        }
    }


    public List<Metadata> findAll() { return repository.findAll(); }

    public List<Metadata> findAllBy(@Nullable String by, @Nullable String order) {
        if (by == null || order == null) {
            return repository.findAll();
        } else if (by.equals("title") && order.equals("asc")) {
            return repository.findAllOrderByTitleAsc();
        } else if (by.equals("title") && order.equals("desc")) {
            return repository.findAllOrderByTitleDesc();
        } else if (by.equals("duration") && order.equals("asc")) {
            return repository.findAllOrderByDurationAsc();
        } else if (by.equals("duration") && order.equals("desc")) {
            return repository.findAllOrderByDurationDesc();
        } else if (by.equals("releaseYear") && order.equals("asc")) {
            return repository.findAllOrderByYearAsc();
        } else if (by.equals("releaseYear") && order.equals("desc")) {
            return repository.findAllOrderByYearDesc();
        } else if (by.equals("director") && order.equals("asc")) {
            return repository.findAllOrderByDirectorAsc();
        } else if (by.equals("director") && order.equals("desc")) {
            return repository.findAllOrderByDirectorDesc();
        } else {
            return repository.findAll();
        }
    }


    @Transactional
    public void deleteMetadata(Integer id) {
        try{
//            SamTextFormat.Companion.create("del s.").cyan().print();
//            SamTextFormat.Companion.create(
//                    repository.getById(id).getTitle()
//            ).yellow().print();
////            repository.deleteById(id); // todo: stg is wrong
////            repository.customDelete(id); // todo: stg is wrong
//            repository.deleteByTitle(
//                    repository.getById(id).getTitle()
//            );
            repository.deleteById(id);

        }catch (Exception e){
            SamTextFormat.Companion.errorMessage(e.getMessage());
        }
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