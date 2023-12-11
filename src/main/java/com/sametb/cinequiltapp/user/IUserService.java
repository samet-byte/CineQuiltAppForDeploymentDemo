package com.sametb.cinequiltapp.user;

import java.security.Principal;
import java.util.Optional;

public interface IUserService {

    void changePassword(ChangePasswordRequest request, Principal connectedUser);

    UserDTO findByUsernameOrEmail(Principal connectedUser);

    Optional<User> makeAdmin(String username);

//    Optional<User> findByUsername(String username);
    User findByUsername(String username);

    Optional<User> findByUsernameOrEmail(String username, String email);

    Optional<User> findById(Integer id);

//    void delete(String username);

//    void delete(String username, Principal connectedUser);

//    Optional<User> delete(DeleteUserRequest request, Principal connectedUser);

    User findByIDNonOptional(Integer id);

    Optional<User> delete(String username, DeleteUserRequest request, Principal connectedUser);

//    Optional<User> findByUsernameOrEmail(String usernameOrEmail);


}
