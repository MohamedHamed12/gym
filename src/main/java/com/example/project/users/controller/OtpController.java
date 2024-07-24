package com.example.project.users.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.project.users.dto.OtpRequest;
import com.example.project.users.service.OtpService;

@RestController
@RequestMapping("/account/otp")
public class OtpController {

    private final OtpService otpService;

    @Autowired
    public OtpController(OtpService otpService) {
        this.otpService = otpService;
    }

    @PostMapping("/request")
    public void requestOtp(@RequestParam String email) {
        otpService.generateAndSendOtp(email);
    }

    @PostMapping("/verify")
    public boolean verifyOtp(@RequestBody OtpRequest otpRequest) {
        return otpService.verifyOtp(otpRequest.getEmail(), otpRequest.getOtp());
    }
}
