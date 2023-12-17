package com.sametb.cinequiltapp.config;

import com.sametb.cinequiltapp._custom.SamTextFormat;
import com.sametb.cinequiltapp.auth.AuthenticationRequest;
import com.sametb.cinequiltapp.auth.AuthenticationService;
import com.sametb.cinequiltapp.auth.RegisterRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static com.sametb.cinequiltapp.user.Role.ADMIN;
import static com.sametb.cinequiltapp.user.Role.MANAGER;

/**
 * @author Samet Bayat.
 * Date: 16.12.2023 1:51 PM
 * Project Name: CineQuiltApp
 * ©2023, NONE OF THE RIGHTS RESERVED.
 * MAYBE SOME OF 'EM. WHO KNOWS?
 */

public class DefaultUsers {

    public static void setAndShowDefaultAdminAndManager(AuthenticationService service) {
        try {
            var admin = RegisterRequest.builder()
                    .username("admin")
                    .country("tr")
                    .email("cinequilt@gmail.com")
                    .password("password")
                    .role(ADMIN)
                    .build();

            var manager = RegisterRequest.builder()
                    .username("manager")
                    .country("eth")
                    .email("manager@mail.com")
                    .password("password")
                    .role(MANAGER)
                    .build();

            SamTextFormat.Companion
                    .create("\nAdmin token: \n" + service.register(admin).getAccessToken())
                    .bold()
                    .green()
                    .print();

            SamTextFormat.Companion.create("Manager token: \n" + service.register(manager).getAccessToken())
                    .bold()
                    .yellow()
                    .print();


        } catch (Exception e) {
            SamTextFormat.Companion
                    .create("Default users already exist.")
                    .cyan()
                    .italic()
                    .bold()
                    .print();

//            System.out.println("Error: " + e.getMessage());

            var admin = AuthenticationRequest.builder()
                    .emailOrUsername("admin")
                    .password("password")
                    .build();

            var manager = AuthenticationRequest.builder()
                    .emailOrUsername("manager")
                    .password("password")
                    .build();

            SamTextFormat.Companion
                    .create("\nAdmin   token: \n" + service.authenticate(admin).getAccessToken())
                    .bold()
                    .green()
                    .print();

            SamTextFormat.Companion.create("Manager token: \n" + service.authenticate(manager).getAccessToken())
                    .bold()
                    .yellow()
                    .print();
        }

        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            SamTextFormat.Companion.create("Server IP: " + inetAddress.getHostAddress())
                    .bold()
                    .cyan()
                    .print();
        } catch (UnknownHostException e) {
            SamTextFormat.Companion.errorMessage("Error getting IP address: " + e.getMessage());
        }
    }

    @Bean
    public CommandLineRunner commandLineRunner(
            AuthenticationService service
    ) {
        return args -> setAndShowDefaultAdminAndManager(service);
    }
}
