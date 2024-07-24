package com.example.project.users.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenRequest {
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
