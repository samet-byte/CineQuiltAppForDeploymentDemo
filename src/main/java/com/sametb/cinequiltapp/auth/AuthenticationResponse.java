package com.sametb.cinequiltapp.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {


  @JsonProperty("accessToken")
  private String accessToken;
  @JsonProperty("refreshToken")
  private String refreshToken;

//    @Nullable
    @JsonProperty("userId")
    private Integer userId;

    @JsonProperty("username")
    private String username;

    @JsonProperty("email")
    private String email;

    @JsonProperty("country")
    private String country;

  @JsonProperty("roles")
  private List<String> roles;

}
