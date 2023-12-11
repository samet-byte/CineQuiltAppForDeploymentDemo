package com.sametb.cinequiltapp.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
  //todo: change to usernameOrPassword
  private String emailOrUsername;
  private String password;
}
