package com.sametb.cinequiltapp.demo;

import com.sametb.cinequiltapp._custom.SamTextFormat;
import com.sametb.cinequiltapp.exception.MetadataNotFoundException;
import com.sametb.cinequiltapp.metadata.Metadata;
import com.sametb.cinequiltapp.metadata.MetadataRequest;
import com.sametb.cinequiltapp.metadata.MetadataService;
import com.sametb.cinequiltapp.user.User;
import com.sametb.cinequiltapp.user.UserService;
//import com.sametb.cinequiltapp.user.User;
//import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@Deprecated
@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminController {

    private final MetadataService service;

    private final UserService userService;

    //////////////////////////////////////////////////////////////////////////////////// create - insert
    @PostMapping
    @PreAuthorize("hasAuthority('admin:create')")
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
            String serachTitle = decodeString(title);
            SamTextFormat.Companion.create("serachTitle: " + serachTitle).yellow().print();
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
//        return ResponseEntity.ok(service.findByTitleContainingOrDirectorContainingOrYearContaining(query));
        return ResponseEntity.ok(service.findByTitleContainingOrDirectorContaining(query));
    }    //////////////////////////////////////////////////////////////////////////////////// update - modify
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<Metadata> updateMetadata(@RequestBody Metadata metadata, @PathVariable Integer id) {
        return ResponseEntity.ok(service.updateMetadataById(id, metadata)
                .orElseThrow(() -> new MetadataNotFoundException("Metadata not found with this ID!")));
    }

    //////////////////////////////////////////////////////////////////////////////////// delete - remove
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<?> deleteMetadata(@PathVariable Integer id) {
        Metadata deletedMetadata = service.findById(id);
        service.deleteMetadata(id);
        return ResponseEntity.accepted().body(deletedMetadata); //.build();
    }

    //////////////////////////////////////////////////////////////////////////////////// user stuff


    //todo: works but not as expected, returns exceptions
    @PutMapping("/make-admin/{username}")
    public ResponseEntity<?> makeAdmin(@PathVariable String username) {
        Optional<User> uUser = userService.makeAdmin(username);
        return ResponseEntity.ok(uUser);
//        return ResponseEntity.ok(userService.findByUsername(username));
    }


/*

    @GetMapping("/users")
    public ResponseEntity<?> findAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> findUserById(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Integer id) {
        userService.deleteUserById(id);
        return ResponseEntity.accepted().build();
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUserById(@PathVariable Integer id, @RequestBody User user) {
        return ResponseEntity.ok(userService.updateUserById(id, user));
    }

    @GetMapping("/users/username/{username}")
    public ResponseEntity<?> findUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.findByUsername(username));
    }

    @GetMapping("/users/email/{email}")
    public ResponseEntity<?> findUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.findByEmail(email));
    }

    @GetMapping("/users/search/{query}")
    public ResponseEntity<?> findUserByQuery(@PathVariable String query) {
        return ResponseEntity.ok(userService.findByUsernameContainingOrEmailContaining(query));
    }

    @GetMapping("/users/roles/{role}")
    public ResponseEntity<?> findUserByRole(@PathVariable String role) {
        return ResponseEntity.ok(userService.findByRole(role));
    }

    @GetMapping("/users/roles/search/{query}")
    public ResponseEntity<?> findUserByRoleQuery(@PathVariable String query) {
        return ResponseEntity.ok(userService.findByRoleContaining(query));
    }

    @GetMapping("/users/roles/contains/{role}")
    public ResponseEntity<?> findUserByRoleContains(@PathVariable String role) {
        return ResponseEntity.ok(userService.findByRoleContaining(role));
    }

*/



}
