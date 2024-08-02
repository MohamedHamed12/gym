package com.example.project.users.service;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.mail.SimpleMailMessage;
// import org.springframework.mail.javamail.JavaMailSender;
// import org.springframework.stereotype.Service;

// // @Service
// // public class EmailSenderService {

// //     private final JavaMailSender mailSender;

// //     // @Autowired
// //     public EmailSenderService(JavaMailSender mailSender) {
// //         this.mailSender = mailSender;
// //     }

// //     public void sendOtpEmail(String to, String otp) {
// //         SimpleMailMessage message = new SimpleMailMessage();
// //         message.setTo(to);
// //         message.setSubject("Your OTP Code");
// //         message.setText("Your OTP code is: " + otp);
// //         mailSender.send(message);
// //     }
// // }

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

// @Service
// public class EmailSenderService {

//     private static final Logger logger = LoggerFactory.getLogger(EmailSenderService.class);

//     private final JavaMailSender mailSender;

//     public EmailSenderService(JavaMailSender mailSender) {
//         this.mailSender = mailSender;
//     }

//     public void sendOtpEmail(String to, String otp) {
//         try {
//             SimpleMailMessage message = new SimpleMailMessage();
//             message.setTo(to);
//             message.setSubject("Your OTP Code");
//             message.setText("Your OTP code is: " + otp);
//             mailSender.send(message);
//             logger.info("OTP email sent to {}", to);
//         } catch (Exception e) {
//             logger.error("Failed to send OTP email to {}", to, e);
//         }
//     }
// }


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendOtpEmail(String to, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("backendbackend90@gmail.com");
        message.setTo(to);
        message.setSubject("subject");
        message.setText("Your OTP code is: " + otp);

        mailSender.send(message);
    }
}