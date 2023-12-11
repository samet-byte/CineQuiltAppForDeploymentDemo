package com.sametb.cinequiltapp.metadata;

import com.sametb.cinequiltapp._custom.SamTextFormat;
import com.sametb.cinequiltapp.exception.MetadataNotFoundException;
import com.sametb.cinequiltapp.fav.IFavService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/api/v1/metadatas")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000") //!! allowing client application with port 3000 to consume the backed
//@CrossOrigin("*")
public class MetadataController {

    private final MetadataService service;

    //////////////////////////////////////////////////////////////////////////////////// create - insert
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')") // !! MANAGER manager:create is better
//    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<?> save(
            @RequestBody MetadataRequest request
    ) {
        Metadata savedMetadata = service.save(request);
        return ResponseEntity.accepted().body(savedMetadata); //.build();
    }

    //////////////////////////////////////////////////////////////////////////////////// find - search

//    @GetMapping
//    public ResponseEntity<List<Metadata>> findAllMetadatas() {
//        return ResponseEntity.ok(service.findAll());
//    }
    @GetMapping
    public ResponseEntity<List<Metadata>> findAllMetadatasBy(
            @RequestParam(required = false) String by,
            @RequestParam(required = false) String order
    ) {
        if (by != null && order != null) {
            return ResponseEntity.ok(service.findAllBy(by, order));
        }
        return ResponseEntity.ok(service.findAll());
    }

//    @GetMapping("{id}")
    @GetMapping("/{id}")
    public ResponseEntity<Metadata> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<List<Metadata>> findByTitle(@PathVariable String title) {
        return ResponseEntity.ok(service.findByTitleContaining(title));
    }
    @GetMapping("/title/single/{title}")
    public ResponseEntity<Metadata> findByTitleSingle(@PathVariable String title) {
        try {
            String serachTitle = decodeString(title);
//            SamTextFormat.Companion.create("serachTitle: " + serachTitle).yellow().print();
            return ResponseEntity.ok(service.findByTitle(serachTitle));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    String decodeString(String s){
        return URLDecoder.decode(s, StandardCharsets.UTF_8);
    }
    
    @GetMapping("/director/{director}")
    public ResponseEntity<List<Metadata>> findByDirector(@PathVariable String director) {
        return ResponseEntity.ok(service.findByDirectorContaining(director));
    }
    
    @GetMapping("/year/{year}")
    public ResponseEntity<List<Metadata>> findByYear(@PathVariable int year) {
        return ResponseEntity.ok(service.findByReleaseYear(year));
    }
    
    @GetMapping("/duration/{duration}")
    public ResponseEntity<List<Metadata>> findByDuration(@PathVariable int duration) {
        return ResponseEntity.ok(service.findByDuration(duration));
    }
    
    @GetMapping("/search/{query}")
    public ResponseEntity<List<Metadata>> findByQuery(@PathVariable String query) {
        return ResponseEntity.ok(service.findByTitleContainingOrDirectorContainingOrYearContaining(query));
    }
//    @GetMapping("/search/{query}")
//    public ResponseEntity<List<Metadata>> findByQuery(@PathVariable String query) {
//        return ResponseEntity.ok(service.findByTitleContainingOrDirectorContaining(query, query));
//    }

    //////////////////////////////////////////////////////////////////////////////////// update - modify
    @PutMapping("/{id}")
//    @PreAuthorize("hasAuthority('admin:update')")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Metadata> updateMetadata(@RequestBody Metadata metadata, @PathVariable Integer id) {
        return ResponseEntity.ok(service.updateMetadataById(id, metadata)
                .orElseThrow(() -> new MetadataNotFoundException("Metadata not found with this ID!")));
    }



    private final IFavService favoritesService;

    //////////////////////////////////////////////////////////////////////////////////// delete - remove
    //@Transactional
    @DeleteMapping("/{id}")
    //@PreAuthorize("hasRole('ADMIN')")//todo: ADMIN
//    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<Metadata> deleteMetadata(
            @PathVariable Integer id
    ) {
//        Metadata deletedMetadata = service.findById(id);
//        favoritesService.deleteFavourite(id);
        try{
            favoritesService.deleteFavouriteByMetadataId(id);
            service.deleteMetadata(id);
            return ResponseEntity.accepted().build(); // .body(deletedMetadata); //.build();}
    } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }



}



