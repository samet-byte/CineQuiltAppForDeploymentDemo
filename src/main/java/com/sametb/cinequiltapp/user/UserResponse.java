package com.sametb.cinequiltapp.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Samet Bayat.
 * Date: 30.11.2023 12:06 AM
 * Project Name: CineQuiltApp
 * ©2023, NONE OF THE RIGHTS RESERVED.
 * MAYBE SOME OF 'EM. WHO KNOWS?
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Integer id;
    private String username;
    private String email;
    private String country;
    private LocalDateTime createTime;
    private Role role;
    private List<String> authorities;
    private LocalDateTime currentTimestamp;

    @NotNull
    public static UserResponse fromUser(@NotNull User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setCurrentTimestamp(LocalDateTime.now());
        userResponse.setId(user.getId());
        userResponse.setUsername(user.getUsername());
        userResponse.setEmail(user.getEmail());
        userResponse.setCountry(user.getCountry());
        userResponse.setCreateTime(user.getCreateTime());
        userResponse.setRole(user.getRole());
        userResponse.setAuthorities(user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());
        return userResponse;
    }

    public static List<UserResponse> fromUsers(@NotNull List<User> users) {
        return users.stream().map(UserResponse::fromUser).toList();
    }



}
