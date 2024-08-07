package com.example.project.users.service;



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