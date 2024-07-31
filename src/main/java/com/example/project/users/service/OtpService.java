package com.example.project.users.service;

import com.example.project.users.entity.Otp;
import com.example.project.users.entity.User;
import com.example.project.users.repository.OtpRepository;
import com.example.project.users.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Random;

@Service
public class OtpService {

    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    private EmailSenderService emailService; // Assuming you have a service to send emails
    @Autowired
    private UserRepository userRepository;

    private static final long OTP_VALID_DURATION = 5 * 60 * 1000; // 5 minutes

   
      private String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }
    public void generateAndSendOtp(String email) {
            String otpCode = generateOtp();
            Otp otp = new Otp();


            User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));


            otp.setUser(user);
            otp.setOtp(otpCode);
            // otp.setExpirationTime(Instant.now().plus(OTP_VALID_DURATION, ChronoUnit.MINUTES));
            otp.setExpirationTime(Instant.now().plus(15, ChronoUnit.MINUTES).toEpochMilli());

            otpRepository.save(otp);

            // Send the OTP to the user's email
            emailService.sendOtpEmail(email, otpCode);
        }
    
    public boolean verifyOtp( String otp) {
        Otp otpEntity = otpRepository.findByOtp( otp)
                .orElseThrow(() -> new IllegalArgumentException("Invalid OTP"));

        // Check if the OTP has expired
        if (Instant.now().toEpochMilli() > otpEntity.getExpirationTime()) {
            otpRepository.delete(otpEntity);
            throw new IllegalArgumentException("OTP has expired");
        }


        return true;
    }
}
