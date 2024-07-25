package com.example.project.users.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenRequest {

    @NotBlank(message = "Token is required")
    private String token;
}

// public class RefreshTokenRequest {

// 	private String token;
	
// 	public RefreshTokenRequest() {

// 	}

// 	public String getToken() {
// 		return token;
// 	}

// 	public void setToken(String token) {
// 		this.token = token;
// 	}

// }
