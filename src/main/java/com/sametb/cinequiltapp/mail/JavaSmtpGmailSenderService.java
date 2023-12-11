package com.sametb.cinequiltapp.mail;

/**
 * @author Samet Bayat.
 * Date: 30.11.2023 1:34 PM
 * Project Name: CineQuiltApp
 * ©2023, NONE OF THE RIGHTS RESERVED.
 * MAYBE SOME OF 'EM. WHO KNOWS?
 */



import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class JavaSmtpGmailSenderService {
    @Autowired
    private JavaMailSender emailSender;

    public void sendEmail(String toEmail, String subject, String body){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("CineQuilt");
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);


        emailSender.send(message);

        System.out.println("Message sent successfully");
    }


    public void sendEmailWithAttachment(String toEmail, String subject, String body, String attachmentPath) {
        MimeMessage message = emailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("CineQuilt");
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(body);

            // Add attachment
            if (attachmentPath != null && !attachmentPath.isEmpty()) {
                FileSystemResource file = new FileSystemResource(new File(attachmentPath));
                helper.addAttachment("AttachmentFileName", file);
            }

            emailSender.send(message);

            System.out.println("Message with attachment sent successfully");
        } catch (MessagingException e) {
            System.out.println("Error sending message with attachment: " + e.getMessage());
        }
    }


    //todo: Kotlin with default parameters

    public void sendEmailWithImageAndLink(String toEmail, String subject, String body, String imagePath, String webpageLink) {
        MimeMessage message = emailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("CineQuilt");
            helper.setTo(toEmail);
            helper.setSubject(subject);

            // HTML content with embedded image and link
            String htmlContent = "<p>" + body + "</p><br/>" +
                    "<a href='" + webpageLink + "'>" +
                    "<img src='cid:image' alt='Direct to CineQuilt'></a>";

            helper.setText(htmlContent, true);

            // Add inline image
            if (imagePath != null && !imagePath.isEmpty()) {
                FileSystemResource image = new FileSystemResource(new File(imagePath));
                helper.addInline("image", image);
            }

            emailSender.send(message);

            System.out.println("Email with image and link sent successfully");
        } catch (MessagingException e) {
            System.out.println("Error sending email with image and link: " + e.getMessage());
        }
    }

}
