package com.sametb.cinequiltapp.auth;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sametb.cinequiltapp._custom.SamTextFormat;
import com.sametb.cinequiltapp.config.JwtService;
import com.sametb.cinequiltapp.token.Token;
import com.sametb.cinequiltapp.token.TokenRepository;
import com.sametb.cinequiltapp.token.TokenType;
import com.sametb.cinequiltapp.user.User;
import com.sametb.cinequiltapp.user.Role;
import com.sametb.cinequiltapp.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Enumeration;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserRepository repository;
  private final TokenRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public AuthenticationResponse register(RegisterRequest request) {
    Role userRole = (request.getRole() != null) ? request.getRole() : Role.USER; // if role is not specified, set it to USER
    var user = User.builder()
        .username(request.getUsername())
        .country(Optional.ofNullable(request.getCountry()).orElse("N/A"))
        .email(request.getEmail())
        .createTime(Optional.ofNullable(request.getCreateTime()).orElse(LocalDateTime.now()))
        .password(passwordEncoder.encode(request.getPassword()))
        .role(userRole)
        .build();
    var savedUser = repository.save(user);
    var jwtToken = jwtService.generateToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);
    saveUserToken(savedUser, jwtToken);
    return AuthenticationResponse.builder()
            .accessToken(jwtToken)
            .refreshToken(refreshToken)

            .country(request.getCountry())
            .username(request.getUsername())
            .email(request.getEmail())

            .build();
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getEmailOrUsername(),
            request.getPassword()
        )
    );
    var user = repository.findByUsernameOrEmail(request.getEmailOrUsername(), request.getEmailOrUsername())
        .orElseThrow();
    var jwtToken = jwtService.generateToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);
    revokeAllUserTokens(user);
    saveUserToken(user, jwtToken);
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

  private void saveUserToken(User user, String jwtToken) {
    var token = Token.builder()
        .user(user)
        .token(jwtToken)
        .tokenType(TokenType.BEARER)
        .expired(false)
        .revoked(false)
        .build();
    tokenRepository.save(token);
  }

  private void revokeAllUserTokens(User user) {
    var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
    if (validUserTokens.isEmpty())
      return;
    validUserTokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
    });
    tokenRepository.saveAll(validUserTokens);
  }



  public AuthenticationResponse refreshToken(
          @NotNull HttpServletRequest request
//          , HttpServletResponse response
  ) throws IOException {


    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

    final String refreshToken;
    final String usernameOrEmail;
    if (authHeader == null
            ||
            !authHeader.startsWith("Bearer ")) {
//      SamTextFormat.Companion.create("refreshToken: authHeader == null || !authHeader.startsWith(\"Bearer \")").red().bold().print();
    }
    refreshToken = authHeader.substring(7); //Bearer_ length = 7
    usernameOrEmail = jwtService.extractUsername(refreshToken);
    if (usernameOrEmail != null) {
      var user = this.repository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
              .orElseThrow();
      if (jwtService.isTokenValid(refreshToken, user)) {
        var accessToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);
        var authResponse = AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .roles(user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList()) ///todo: roles
                .userId(user.getId())
                .country(user.getCountry())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
//        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
        ///

        return authResponse;
      }
    }
    return null;
  }

}
