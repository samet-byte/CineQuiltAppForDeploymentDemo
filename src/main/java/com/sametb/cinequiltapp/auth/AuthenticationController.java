package com.sametb.cinequiltapp.auth;

import com.sametb.cinequiltapp.JavaSmtpGmailSenderService;
import com.sametb.cinequiltapp._custom.SamTextFormat;
import com.sametb.cinequiltapp.config.LogoutService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000")
public class AuthenticationController {

  private final AuthenticationService service;
  private final LogoutService logoutService;
  private final JavaSmtpGmailSenderService gmailSenderService;

  @PostMapping("/register")
  public ResponseEntity<AuthenticationResponse> register(
      @RequestBody RegisterRequest request
  ) {
    try {
      AuthenticationResponse authenticationResponse = service.register(request);
      gmailSenderService.sendEmail(request.getEmail(), "Welcome " + request.getUsername(), "Let our cinematic warmth wrap you with joy!");
      return ResponseEntity.ok(authenticationResponse);
//      return ResponseEntity.ok(service.register(request));
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.badRequest().build();
    }
  }
  @PostMapping("/authenticate")
  public ResponseEntity<AuthenticationResponse> authenticate(
      @RequestBody AuthenticationRequest request,
        HttpServletResponse response
  ) {
      AuthenticationResponse authenticationResponse = service.authenticate(request);


    Cookie cookie = new Cookie("refreshToken", authenticationResponse.getRefreshToken());
//    cookie.setHttpOnly(true); // This makes the cookie accessible only through the HTTP protocol
    cookie.setMaxAge(7 * 24 * 60 * 60); // Set the expiration time in seconds (adjust as needed)
    cookie.setPath("/"); // Set the cookie path (adjust as needed)
    response.addCookie(cookie);
    return ResponseEntity.ok(authenticationResponse);
  }

  //todo: response entity?
  @GetMapping("/refresh")
//  @GetMapping("/refresh-token")
  public ResponseEntity<AuthenticationResponse> refreshToken(
//        Principal connectedUser
      HttpServletRequest request
//          , HttpServletResponse response
  ) throws IOException {
    AuthenticationResponse responseRefresh = service.refreshToken(
//            connectedUser
            request
//            , response
    );
    SamTextFormat.Companion.create(responseRefresh.toString()).magenta().bold().print();
    return ResponseEntity.ok(responseRefresh);
  }

//  //todo: response entity?
//  @PostMapping("/refresh-token")
//  public void refreshToken(
//      HttpServletRequest request,
//      HttpServletResponse response
//  ) throws IOException {
//    service.refreshToken(request, response);
//  }
//
//  //todo: response entity?
//  @PostMapping("/refresh-token")
//  public void refreshToken(
//          HttpServletRequest request,
//          HttpServletResponse response
//  ) throws IOException {
//    AuthenticationResponse responseRefresh = service.refreshToken(request, response);
////    response.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + responseRefresh.getAccessToken());
//    return ResponseEntity.ok(responseRefresh);
//  }


  @Deprecated
  @PostMapping("/refresh-token")
  public void refreshToken1(
          HttpServletRequest request,
          HttpServletResponse response
  ) throws IOException {
    service.refreshToken1(request, response);
  }


  //log-out ???????
    @PostMapping("/logout")
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        logoutService.logout(request, response, null);
    }


}
