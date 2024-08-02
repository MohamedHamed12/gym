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
    public ResponseEntity<?> requestOtp(@Valid @RequestBody EmailRequest emailRequest) {
        // otpService.generateAndSendOtp(emailRequest.getEmail());
        try {
            otpService.generateAndSendOtp(emailRequest.getEmail());
            SuccessResponse response = new SuccessResponse(
                    "User registered successfully.otp sent  Please confirm your email.");

            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            ErrorResponse errorResponse = new ErrorResponse("Internal  Error " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // @PreAuthorize("permitAll()")
    @PostMapping("/verify")
    public ResponseEntity<?> verifyOtp(@Valid @RequestBody OtpRequest otpRequest) {
        try {

            if (otpService.verifyOtp(otpRequest.getOtp())) {
                   SuccessResponse response = new SuccessResponse("OTP verified successfully");

              
                return ResponseEntity.ok(response);
            } else {
            
                ErrorResponse errorResponse = new ErrorResponse("cannot verify" );

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }
        }
        // return otpService.verifyOtp( otpRequest.getOtp());
        catch (Exception e) {
            // Handle other potential exceptions
        
            ErrorResponse errorResponse = new ErrorResponse("Internal  Error message" + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
