package com.example.project.users.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    private String firstname;
    private String lastname;
    private String email;
    private String password;
}



// public class RegisterRequest {
	
// 	private String firstname;
// 	private String lastname;
// 	private String email;
// 	private String password;
	
// 	public RegisterRequest() {
		
// 	}

// 	public String getFirstname() {
// 		return firstname;
// 	}

// 	public void setFirstname(String firstname) {
// 		this.firstname = firstname;
// 	}

// 	public String getLastname() {
// 		return lastname;
// 	}

// 	public void setLastname(String lastname) {
// 		this.lastname = lastname;
// 	}

// 	public String getEmail() {
// 		return email;
// 	}

// 	public void setEmail(String email) {
// 		this.email = email;
// 	}

// 	public String getPassword() {
// 		return password;
// 	}

// 	public void setPassword(String password) {
// 		this.password = password;
// 	}

// }

// package com.example.project.users.dto;

