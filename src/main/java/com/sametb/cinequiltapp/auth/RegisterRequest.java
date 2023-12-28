package com.sametb.cinequiltapp.auth;

import com.sametb.cinequiltapp.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
  private String username;
  private String email;
  private String password;
  private String country;
  private LocalDateTime createTime;
  private Role role;
}
