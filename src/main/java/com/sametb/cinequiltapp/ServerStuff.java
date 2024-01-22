package com.sametb.cinequiltapp;

import lombok.Getter;

/**
 * @author Samet Bayat.
 * Date: 22.01.2024 4:19 AM
 * Project Name: CineQuiltApp
 * ©2024, NONE OF THE RIGHTS RESERVED.
 * MAYBE SOME OF 'EM. WHO KNOWS?
 */

@Getter
public class ServerStuff {
        public static final String baseUrl = "/api/v1";
        public static final String authorizationHeader = "Authorization";

        public static final String bearer_ = "Bearer ";

        public static final String auth = baseUrl + "/auth";

        public static final String secretKey = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";

        public static final String gptKey = "key";
        public static final Long jwtExp = 86400000L;
        public static final String authLogout = "api/v1/logout";
    }
