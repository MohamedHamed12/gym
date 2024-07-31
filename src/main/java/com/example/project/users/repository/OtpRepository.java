package com.example.project.users.repository;

import com.example.project.users.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;


public interface OtpRepository extends JpaRepository<Otp, Long> {
    // Optional<Otp> findByEmailAndOtp(String email, String otp);
    Optional<Otp> findByOtp(String otp);
    // List<Otp> findByEmail(String email);

}
