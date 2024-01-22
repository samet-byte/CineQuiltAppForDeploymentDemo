package com.sametb.cinequiltapp.config;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static com.sametb.cinequiltapp.config.HttpMethods.*;

/**
 * @author Samet Bayat.
 * Date: 2.12.2023 4:39 PM
 * Project Name: CineQuiltApp
 * ©2023, NONE OF THE RIGHTS RESERVED.
 * MAYBE SOME OF 'EM. WHO KNOWS?
 */

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
//    private final ServerProperties serverProperties;
//        registry.addMapping(serverProperties.getApiAllowAll())
//                .allowedOrigins(serverProperties.getOrigin())

    @Override
    public void addCorsMappings(@NotNull CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("https://cinequilt.onrender.com")
                .allowCredentials(true) //TODO: production -> activate
                .allowedMethods(
                        String.valueOf(GET),
                        String.valueOf(POST),
                        String.valueOf(PUT),
                        String.valueOf(DELETE),
                        String.valueOf(PATCH)
                );



    }
}
