package com.example.project.users.dto;

import com.example.project.users.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthenticationRequest {
    private String token;
    private String refreshToken;
    private User user;
}










// package com.example.project.users.dto;

// import com.example.project.users.entity.User;

// public class JwtAuthenticationRequest {

// 	private String token;
// 	private String refreshToken;
// 	private User user;
	
// 	public JwtAuthenticationRequest() {
	
// 	}

// 	public String getToken() {
// 		return token;
// 	}

// 	public void setToken(String token) {
// 		this.token = token;
// 	}

// 	public String getRefreshToken() {
// 		return refreshToken;
// 	}

// 	public void setRefreshToken(String refreshToken) {
// 		this.refreshToken = refreshToken;
// 	}

// 	public User getUser() {
// 		return user;
// 	}

// 	public void setUser(User user) {
// 		this.user = user;
// 	}

	
// }
