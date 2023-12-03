package com.sametb.cinequiltapp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


/**
 * @author Samet Bayat.
 * Date: 3.12.2023 9:58 PM
 * Project Name: CineQuiltApp
 * ©2023, NONE OF THE RIGHTS RESERVED.
 * MAYBE SOME OF 'EM. WHO KNOWS?
 */

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class EmailServiceTest {

    @Autowired
    private JavaSmtpGmailSenderService emailService; // Assuming you have an EmailService bean

    @Test
    public void testSendEmailWithImageAndLink() {
        String toEmail = "samaudiobook@gmail.com";
        String subject = "Let the cinematic warmth wrap you!";
        String body = "";
        String imagePath = "src/main/assets/rose_pedal_heart.png";
        String webpageLink = "https://sametb.com";

        emailService.sendEmailWithImageAndLink(toEmail, subject, body, imagePath, webpageLink);
        // Add assertions or logging as needed for your testing
    }
}

