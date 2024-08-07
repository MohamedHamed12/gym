package com.example.project.users.entity;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.project.users.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customuser")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstname;
    private String lastname;

    @Column(unique = true)
    private String email;

    private String password;
    private Role role=Role.User;


	private Boolean emailConfirmed=  false;

    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore

    private Otp otp;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}




// package com.example.project.users.entity;

// import java.util.Collection;
// import java.util.List;

// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.core.userdetails.UserDetails;

// import com.example.project.users.enums.Role;

// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.Table;

// @Entity
// @Table(name = "customuser")

// public class User implements UserDetails{

// 	@Id
// 	@GeneratedValue(strategy = GenerationType.IDENTITY) // Or another appropriate strategy
// 	private long id;
// 	private String firstname;
// 	private String lastname;

// 	@Column(unique = true)
// 	private String email;
	
// 	private String password;
// 	private Role role;
	
	// public long getId() {
	// 	return id;
	// }
	// public void setId(long id) {
	// 	this.id = id;
	// }
	// public String getFirstname() {
	// 	return firstname;
	// }
	// public void setFirstname(String firstname) {
	// 	this.firstname = firstname;
	// }
	// public String getLastname() {
	// 	return lastname;
	// }
	// public void setLastname(String lastname) {
	// 	this.lastname = lastname;
	// }
	// public String getEmail() {
	// 	return email;
	// }
	// public void setEmail(String email) {
	// 	this.email = email;
	// }
	// public String getPassword() {
	// 	return password;
	// }
	// public void setPassword(String password) {
	// 	this.password = password;
	// }
	// public Role getRole() {
	// 	return role;
	// }
	// public void setRole(Role role) {
	// 	this.role = role;
	// }
// 	@Override
// 	public Collection<? extends GrantedAuthority> getAuthorities() {
// 		return List.of(new SimpleGrantedAuthority(role.name()));
// 	}
// 	@Override
// 	public String getUsername() {
// 		return email;
// 	}
// 	@Override
// 	public boolean isAccountNonExpired() {
// 		return true;
// 	}
// 	@Override
// 	public boolean isAccountNonLocked() {
// 		return true;
// 	}
// 	@Override
// 	public boolean isCredentialsNonExpired() {
// 		return true;
// 	}
// 	@Override
// 	public boolean isEnabled() {
// 		return true;
// 	}
	
	
	
	
// }
