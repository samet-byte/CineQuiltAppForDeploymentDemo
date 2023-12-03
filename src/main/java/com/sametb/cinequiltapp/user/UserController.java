package com.sametb.cinequiltapp.user;


import com.sametb.cinequiltapp.favs.FavouriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
//@CrossOrigin("*")
@CrossOrigin("http://localhost:3000")
public class UserController {

    private final UserService service;

    private final FavouriteService userFavoritesService;

//
//    @GetMapping("/favs")
//    public ResponseEntity<List<UserFavourites>> getAllUserFavourites() {
//        return ResponseEntity.ok(userFavoritesService.getAllUserFavorites());
//    }
//
//
//    @PostMapping("/favs")
//    public UserFavourites saveUserFavourites(@RequestBody UserFavourites UserFavourites) {
//        return userFavoritesService.saveUserFavorites(UserFavourites);
//    }


    @GetMapping //todo: principal?
    public ResponseEntity<?> findByUsernameOrEmail(
            Principal connectedUser
    ) {
        Optional<UserDTO> user = (Optional.ofNullable(service.findByUsernameOrEmail(connectedUser)));
//        Optional<User> user = Optional.ofNullable(service.findByUsernameOrEmail(connectedUser));
        return ResponseEntity.ok().body(user);
    }

   @GetMapping("/all")
   @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDTO>> findAll(
//            Principal connectedUser
    ) {
        try {
            return ResponseEntity.ok().body(service.findAllDTO());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
   }

    @PatchMapping
    public ResponseEntity<?> changePassword(
          @RequestBody ChangePasswordRequest request,
          Principal connectedUser
    ) {
        service.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }


    //todo: stg wrong with delete
    //WARN 5463 --- [nio-8080-exec-4] .w.s.m.s.DefaultHandlerExceptionResolver : Resolved [org.springframework.http.converter.HttpMessageNotReadableException: JSON parse error: Cannot construct instance of `com.sametb.cinequiltapp.user.DeleteUserRequest` (although at least one Creator exists): cannot deserialize from Object value (no delegate- or property-based Creator)]
    @DeleteMapping("/{username}")
    public ResponseEntity<?> delete(
            @PathVariable String username,
            @RequestBody DeleteUserRequest request,
            Principal connectedUser
    ) {
        Optional<User> dUser = service.delete(username, request, connectedUser);
        return ResponseEntity.ok().body(dUser);
    }
}
