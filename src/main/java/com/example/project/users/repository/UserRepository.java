package com.example.project.users.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.project.users.entity.User;
import com.example.project.users.enums.Role;


public interface UserRepository extends JpaRepository<User,Long>{
	
	public Optional<User> findByEmail(String email);
	public User findByRole(Role role);
	boolean existsByEmail(String email);
	boolean existsByEmailAndEmailConfirmed(String email, Boolean emailConfirmed);


}

