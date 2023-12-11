package com.sametb.cinequiltapp.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sametb.cinequiltapp._custom.SamTextFormat;
import com.sametb.cinequiltapp.mail.JavaSmtpGmailSenderService;
import com.sametb.cinequiltapp.config.LogoutService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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
      //todo: mail with image
      gmailSenderService.sendEmail(request.getEmail(), "Welcome " + request.getUsername(), "Let our cinematic warmth wrap you with joy!");
      return ResponseEntity.ok(authenticationResponse);
//      return ResponseEntity.ok(service.register(request));
    } catch (Exception e) {
      SamTextFormat.Companion.create(e.getMessage()).red().bold().print();
      return ResponseEntity.badRequest().build();
    }
  }

  @PostMapping("/authenticate")
  public ResponseEntity<?> authenticate(
        @RequestBody AuthenticationRequest request,  // todo : @RequestBody
        HttpServletResponse response
  ) {
        AuthenticationResponse authenticationResponse = service.authenticate(request);

        Cookie cookie = new Cookie("refreshToken", authenticationResponse.getRefreshToken());
    //    cookie.setHttpOnly(true); // This makes the cookie accessible only through the HTTP protocol
        cookie.setMaxAge(7 * 24 * 60 * 60); // Set the expiration time in seconds (adjust as needed)
        cookie.setPath("/"); // Set the cookie path (adjust as needed)
        response.addCookie(cookie);

        try {
          ObjectMapper objectMapper = new ObjectMapper();
          String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(authenticationResponse);
          return ResponseEntity.ok(json);
        }catch (Exception e){
          SamTextFormat.Companion.create(e.getMessage()).red().bold().print();
          return ResponseEntity.ok(authenticationResponse);
        }


  }


  @GetMapping("/refresh")
  public ResponseEntity<AuthenticationResponse> refreshToken(
      HttpServletRequest request
  ) throws IOException {
    AuthenticationResponse responseRefresh = service.refreshToken(request);
    return ResponseEntity.ok(responseRefresh);
  }

    @PostMapping("/logout")
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        logoutService.logout(request, response, null);
    }


}
