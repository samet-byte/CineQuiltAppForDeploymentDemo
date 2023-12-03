package com.sametb.cinequiltapp;

import com.sametb.cinequiltapp._custom.SamTextFormat;
import com.sametb.cinequiltapp.auth.AuthenticationRequest;
import com.sametb.cinequiltapp.auth.AuthenticationService;
import com.sametb.cinequiltapp.auth.RegisterRequest;
import com.sametb.cinequiltapp.config.ApiController;
import com.sametb.cinequiltapp.exception.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static com.sametb.cinequiltapp.user.Role.ADMIN;
import static com.sametb.cinequiltapp.user.Role.MANAGER;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class CineQuiltAppApplication {

//    @Autowired
//    private JavaSmtpGmailSenderService senderService;


    public static void main(String[] args) {
        SpringApplication.run(CineQuiltAppApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(
            AuthenticationService service
    ) {
        return args -> {
            setAndShowDefaultAdminAndManager(service);
        };
    }


//    @EventListener(ApplicationReadyEvent.class)
//    public void sendMail(){
//        senderService.sendEmail("sambaskent@gmail.com","This is subject","This is email body");
//    }

    private static void setAndShowDefaultAdminAndManager(AuthenticationService service) {
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

            System.out.println("Error: " + e.getMessage());

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
            // Handle the exception (e.g., log or return a default value)
            e.printStackTrace();
            SamTextFormat.Companion.create("Unable to retrieve IP address")
                    .bold()
                    .red()
                    .print();
        }

    }
}
