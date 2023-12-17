package com.sametb.cinequiltapp.config;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author Samet Bayat.
 * Date: 16.12.2023 12:44 AM
 * Project Name: CineQuiltApp
 * ©2023, NONE OF THE RIGHTS RESERVED.
 * MAYBE SOME OF 'EM. WHO KNOWS?
 */

@Configuration
@Getter()

public class ServerProperties {

    @Value("${base}")
    private String baseUrl;

    @Value("${header.authorization}")
    private String authorizationHeader;

    @Value("${header.bearer_}")
    private String bearer_;

    @Value("${endpoint.auth}")
    private String auth;

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;

    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;

    @Value("${endpoint.management-allow-all}")
    private String managementAllowAll;

    @Value("${endpoint.auth-logout}")
    private String authLogout;

    @Value("${origin.base}")
    @Getter(AccessLevel.PRIVATE)
    private String originBase;

    @Value("${origin.port}")
    @Getter(AccessLevel.PRIVATE)
    private String originPort;

    private final String origin = String.format("%s:%s", getOriginBase(), getOriginPort());

    @Value("${api-allow-all}")
    private String apiAllowAll;
}
