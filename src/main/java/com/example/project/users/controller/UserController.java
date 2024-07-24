package com.example.project.users.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
// @CrossOrigin("http://localhost:4200")
public class UserController {

	@GetMapping("/hello")
	public ResponseEntity<String> sayhello(){
		return ResponseEntity.ok("Hi USER");
	}
}
