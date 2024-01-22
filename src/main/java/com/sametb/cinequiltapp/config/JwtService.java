package com.sametb.cinequiltapp.config;


import com.sametb.cinequiltapp.ServerStuff;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {

//  private final ServerProperties serverProperties;

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public <T> T extractClaim(String token, @NotNull Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  public String generateToken(UserDetails userDetails) {
    return generateToken(new HashMap<>(), userDetails);
  }

  public String generateToken(
      Map<String, Object> extraClaims,
      UserDetails userDetails
  ) {
//    return buildToken(extraClaims, userDetails, serverProperties.getJwtExpiration());
    return buildToken(extraClaims, userDetails, ServerStuff.jwtExp);
  }

  public String generateRefreshToken(
      UserDetails userDetails
  ) {
    return buildToken(new HashMap<>(), userDetails, ServerStuff.jwtExp);
  }

  private String buildToken(
          Map<String, Object> extraClaims,
          @NotNull UserDetails userDetails,
          long expiration
  ) {

    List<String> roles = new ArrayList<>();
    Map<String, Object> rolesClaim = new HashMap<>();
    userDetails.getAuthorities().forEach(a -> roles.add(a.getAuthority()));
    rolesClaim.put("roles", roles);

    return Jwts
            .builder()
            .setHeaderParam("typ", "JWT")
            .setClaims(extraClaims)
            .setClaims(rolesClaim)
            .setAudience("cinequiltapp")
//            .setPayload("cinequiltapp")
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + expiration))
            .signWith(getSignInKey(), SignatureAlgorithm.HS256)
            .compact();
  }

  public boolean isTokenValid(String token, @NotNull UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  private Claims extractAllClaims(String token) {
    return Jwts
        .parserBuilder()
        .setSigningKey(getSignInKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  @NotNull
  private Key getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode(ServerStuff.secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
