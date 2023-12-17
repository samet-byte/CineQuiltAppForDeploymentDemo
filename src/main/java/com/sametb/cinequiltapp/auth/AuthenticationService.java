package com.sametb.cinequiltapp.auth;


import com.sametb.cinequiltapp.config.JwtService;
import com.sametb.cinequiltapp.token.TokenRepository;
import com.sametb.cinequiltapp.user.User;
import com.sametb.cinequiltapp.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

import static com.sametb.cinequiltapp.auth.AuthenticationBuilder.*;
import static com.sametb.cinequiltapp.token.TokenBuilder.buildTokenWithUserAndJwtToken;


@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final AuthenticationBuilder authenticationBuilder;

    public AuthenticationResponse register(@NotNull RegisterRequest request) {
        var user = authenticationBuilder.buildUser(request);
        var savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        return authenticationResponseBuilder(request, jwtToken, refreshToken);
    }

    public AuthenticationResponse authenticate(@NotNull AuthenticationRequest request) {
        authenticationManager.authenticate(
                getAuthenticationWithCredentials(request)
        );
        var user = repository.findByUsernameOrEmail(request.getEmailOrUsername(), request.getEmailOrUsername()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return authenticationResponseBuilderWithUser(jwtToken, refreshToken, user);
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = buildTokenWithUserAndJwtToken(user, jwtToken);
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(@NotNull User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty()) return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
//        tokenRepository.deleteAll(validUserTokens);
    }

    public AuthenticationResponse refreshToken(
            @NotNull HttpServletRequest request
//          , HttpServletResponse response
    ) {

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String usernameOrEmail;

        assert authHeader != null;
        refreshToken = authHeader.substring(7); //Bearer_ length = 7
        usernameOrEmail = jwtService.extractUsername(refreshToken);
        if (usernameOrEmail != null) {
            var user = this.repository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                return authenticationResponseBuilderWithUser(accessToken, refreshToken, user);
            }
        }
        return null;
    }

}
