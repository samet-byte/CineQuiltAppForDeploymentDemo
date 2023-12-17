package com.sametb.cinequiltapp.auth;

import com.sametb.cinequiltapp.user.Role;
import com.sametb.cinequiltapp.user.User;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author Samet Bayat.
 * Date: 15.12.2023 11:46 PM
 * Project Name: CineQuiltApp
 * ©2023, NONE OF THE RIGHTS RESERVED.
 * MAYBE SOME OF 'EM. WHO KNOWS?
 */

@Component
@RequiredArgsConstructor
public class AuthenticationBuilder {

    private final PasswordEncoder passwordEncoder;

    public User buildUser(
            @NotNull
            RegisterRequest request
    ) {
        Role userRole = (request.getRole() != null) ? request.getRole() : Role.USER;
        return User.builder()
                .username(request.getUsername())
                .country(Optional.ofNullable(request.getCountry()).orElse("N/A"))
                .email(request.getEmail())
                .createTime(Optional.ofNullable(request.getCreateTime()).orElse(LocalDateTime.now()))
                .password(passwordEncoder.encode(request.getPassword()))
                .role(userRole)
                .build();
    }

    public static AuthenticationResponse authenticationResponseBuilder(
            @NotNull RegisterRequest request,
            String jwtToken,
            String refreshToken
    ) {
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .country(request.getCountry())
                .username(request.getUsername())
                .email(request.getEmail())
                .build();
    }

    public static AuthenticationResponse authenticationResponseBuilderWithUser(
            String jwtToken,
            String refreshToken,
            @NotNull User user
    ) {
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .roles(user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .userId(user.getId())
                .country(user.getCountry())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }

    @NotNull
    public static UsernamePasswordAuthenticationToken getAuthenticationWithCredentials(
            @NotNull AuthenticationRequest request
    ) {
        return new UsernamePasswordAuthenticationToken(
                request.getEmailOrUsername(),
                request.getPassword()
        );
    }
}
