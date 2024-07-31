package com.example.project.users.controller;


import com.example.project.users.dto.*;
import com.example.project.users.entity.User;
import com.example.project.users.service.AuthenticationService;
import com.example.project.users.service.OtpService;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/account")
public class PasswordController {

    @Autowired
    private OtpService otpService;

    @Autowired
    private AuthenticationService authenticationService;

    // Existing methods...

    // @PreAuthorize("isAuthenticated()")
    // @PostMapping("/change-password")
    // public ResponseEntity<?> changePassword(@Valid @RequestBody PasswordChangeRequest passwordChangeRequest) {
    //     User user = authenticationService.getCurrentUser(); // Assuming you have a method to get the current user
    //     authenticationService.changePassword(user, passwordChangeRequest);
    //     return ResponseEntity.ok("Password changed successfully");
    // }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody PasswordResetNewPassword passwordResetRequest) {
        authenticationService.resetPassword(passwordResetRequest);
        return ResponseEntity.ok("OTP sent to email");
    }


    // @PostMapping("/reset-request")
    // public ResponseEntity<?> resetPasswordRequest(@Valid @RequestBody PasswordResetRequest passwordResetRequest ) {
    //     authenticationService.passwordResetRequest(passwordResetRequest);
    //     return ResponseEntity.ok("OTP sent to email");
    // }
    @PostMapping("/reset-request")
    public ResponseEntity<Map<String, String>> resetPasswordRequest(@Valid @RequestBody PasswordResetRequest passwordResetRequest) {
        Map<String, String> response = new HashMap<>();
        try {
            authenticationService.passwordResetRequest(passwordResetRequest);
            response.put("message", "OTP sent to email");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Failed to send OTP: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    // @PostMapping("/confirm-reset-password")
    // public ResponseEntity<?> confirmResetPassword(@Valid @RequestBody PasswordResetOtp passwordResetConfirmRequest) {
        
        // authenticationService.confirmResetPassword(passwordResetConfirmRequest);
        // return ResponseEntity.ok("Password reset successfully");
    // }
    //    @PostMapping("/confirm-reset-password")
    // public ResponseEntity<?> confirmResetPassword(@Valid @RequestBody PasswordResetOtp passwordResetConfirmRequest) {
    //     try {
    //         authenticationService.confirmResetPassword(passwordResetConfirmRequest);
    //         SuccessResponse successResponse = new SuccessResponse("Password reset successfully");
    //         return ResponseEntity.ok(successResponse);
    //     } catch (Exception e) {
    //         // Log the error message (optional)
    //         ErrorResponse errorResponse = new ErrorResponse("Password reset failed: " + e.getMessage());
    //         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    //     }
    // }

    // @PostMapping("/confirm-reset-password")
    // public ResponseEntity<?> confirmResetPassword(@Valid @RequestBody PasswordResetOtp passwordResetConfirmRequest) {
    //     String resetToken = authenticationService.confirmResetPassword(passwordResetConfirmRequest);
    //     return ResponseEntity.ok().body(Collections.singletonMap("token", resetToken));
    // }

    @PostMapping("/confirm-reset-password")
public ResponseEntity<?> confirmResetPassword(
        @Valid @RequestBody PasswordResetOtp passwordResetConfirmRequest) {
    try {
        // Call the service to confirm the reset password and get the reset token
        String resetToken = authenticationService.confirmResetPassword(passwordResetConfirmRequest);
        
        // Return the reset token in the response body
        Map<String, String> response = new HashMap<>();
        response.put("token", resetToken);
        return ResponseEntity.ok(response);
    }
     catch (Exception e) {
        // Handle unexpected exceptions
        ErrorResponse errorResponse = new ErrorResponse("Password reset failed: " + e.getMessage());
        // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

    }
}


}
