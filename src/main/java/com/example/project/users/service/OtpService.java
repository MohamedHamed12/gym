package com.example.project.users.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.project.users.entity.User;
import com.example.project.users.repository.UserRepository;
import com.example.project.users.service.EmailSenderService;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class OtpService {

    private final UserRepository userRepository;
    private final EmailSenderService emailSenderService;

    // ConcurrentMap to store OTPs
    private final ConcurrentMap<String, String> otpStorage = new ConcurrentHashMap<>();

    @Autowired
    public OtpService(UserRepository userRepository, EmailSenderService emailSenderService) {
        this.userRepository = userRepository;
        this.emailSenderService = emailSenderService;
    }

    public void generateAndSendOtp(String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));
        String otp = generateOtp();
        otpStorage.put(email, otp);
        emailSenderService.sendOtpEmail(email, otp);
    }

    public boolean verifyOtp(String email, String otp) {
        String storedOtp = otpStorage.get(email);
        if (storedOtp != null && storedOtp.equals(otp)) {
            otpStorage.remove(email);
            return true;
        }
        return false;
    }

    private String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // Generate a 6-digit OTP
        return String.valueOf(otp);
    }
}
