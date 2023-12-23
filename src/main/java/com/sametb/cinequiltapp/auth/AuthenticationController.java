package com.sametb.cinequiltapp.auth;

import com.sametb.cinequiltapp._custom.SamTextFormat;
import com.sametb.cinequiltapp.mail.SmtpGmailSenderService;
import com.sametb.cinequiltapp.config.LogoutService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

import static com.sametb.cinequiltapp._custom.CustomFunsKt.prettyJsonMaker;



@RestController
@RequestMapping("${endpoint.auth}")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000")
public class AuthenticationController {

    private final AuthenticationService service;
    private final LogoutService logoutService;
    private final SmtpGmailSenderService gmailSenderService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
      @RequestBody RegisterRequest request
    ) {
    try {
      AuthenticationResponse authenticationResponse = service.register(request);
        try{
          gmailSenderService.defaultRegisterMail(request.getEmail(), request.getUsername());
        } catch (Exception e){
            SamTextFormat.Companion.errorMessage("Error sending email: " + e.getMessage());
        }
      return ResponseEntity.ok(authenticationResponse);
    } catch (Exception e) {
      SamTextFormat.Companion.create(e.getMessage()).red().bold().print();
      return ResponseEntity.badRequest().build();
    }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(
          @RequestBody AuthenticationRequest request,
          @NotNull HttpServletResponse response
    ) {
        AuthenticationResponse authenticationResponse = service.authenticate(request);
        Cookie cookie = new Cookie("refreshToken", authenticationResponse.getRefreshToken());
    //    cookie.setHttpOnly(true); // This makes the cookie accessible only through the HTTP protocol
        cookie.setMaxAge(7 * 24 * 60 * 60); // Set the expiration time in seconds (adjust as needed)
        cookie.setPath("/"); // Set the cookie path (adjust as needed)
        response.addCookie(cookie);

        try {
            String prettyOutput = prettyJsonMaker(authenticationResponse);
            return ResponseEntity.ok(prettyOutput);
        }catch (Exception e){
          SamTextFormat.Companion.errorMessage("Error: " + e.getMessage());
          return ResponseEntity.ok(authenticationResponse);
        }
    }

    @GetMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> refreshToken(
      HttpServletRequest request
    ) {
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
