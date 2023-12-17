package com.sametb.cinequiltapp.demo;

import com.sametb.cinequiltapp._custom.SamTextFormat;
import com.sametb.cinequiltapp.metadata.Metadata;
import com.sametb.cinequiltapp.metadata.MetadataRequest;
import com.sametb.cinequiltapp.metadata.MetadataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Deprecated
@RestController
@RequestMapping("/api/v1/management")
@Tag(name = "Management")
@PreAuthorize("hasRole('MANAGEMENT')")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000")
public class ManagementController {

    private final MetadataService service;

    @Operation(
            description = "Get endpoint for manager",
            summary = "This is a summary for management get endpoint",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403"
                    )
            }

    )



    //////////////////////////////////////////////////////////////////////////////////// create - insert
    @PostMapping
    @PreAuthorize("hasAuthority('management:create')")
    public ResponseEntity<?> save(
            @RequestBody MetadataRequest request
    ) {
        Metadata savedMetadata = service.save(request);
        return ResponseEntity.accepted().body(savedMetadata); //.build();
    }

    //////////////////////////////////////////////////////////////////////////////////// find - search

    @GetMapping
    public ResponseEntity<List<Metadata>> findAllMetadatas() {
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
            String searchTitle = decodeString(title);
            SamTextFormat.Companion.create("searchTitle: " + searchTitle).yellow().print();
            return ResponseEntity.ok(service.findByTitle(searchTitle));
        } catch (Exception e) {
            SamTextFormat.Companion.errorMessage(e.getMessage());
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
//        return ResponseEntity.ok(service.findByTitleContainingOrDirectorContainingOrYearContaining(query));
        return ResponseEntity.ok(service.findByTitleContainingOrDirectorContaining(query));
    }

}
