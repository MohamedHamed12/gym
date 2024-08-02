package com.example.project.users.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.project.users.dto.EmailRequest;
import com.example.project.users.dto.ErrorResponse;
import com.example.project.users.dto.OtpRequest;
import com.example.project.users.dto.SuccessResponse;
import com.example.project.users.service.OtpService;

import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/account/otp")
public class OtpController {

    private final OtpService otpService;

    @Autowired
    public OtpController(OtpService otpService) {
        this.otpService = otpService;
    }

    @PostMapping("/request")
    public 	 ResponseEntity<?>  requestOtp(@Valid @RequestBody EmailRequest emailRequest) {
        // otpService.generateAndSendOtp(emailRequest.getEmail());
        try {
            otpService.generateAndSendOtp(emailRequest.getEmail());
            SuccessResponse response = new SuccessResponse("User registered successfully.otp sent  Please confirm your email.");

            return ResponseEntity.ok(response);
        }  catch (Exception ex) {
            ErrorResponse errorResponse = new ErrorResponse("Internal  Error "+ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // @PreAuthorize("permitAll()")
    @PostMapping("/verify")
    public ResponseEntity<Map<String, Object>> verifyOtp(@Valid @RequestBody OtpRequest otpRequest) {

        Map<String, Object> response = new HashMap<>();

        if (otpService.verifyOtp(otpRequest.getOtp())) {
            response.put("success", true);
            response.put("message", "OTP verified successfully");
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "Invalid OTP");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // return otpService.verifyOtp( otpRequest.getOtp());
    }
}
