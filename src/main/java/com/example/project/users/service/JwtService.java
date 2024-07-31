package com.example.project.users.service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;


@Service
public class JwtService {

	public String generateToken(UserDetails userDetails) {
		return Jwts.builder()
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
				.signWith(getSignKey(), SignatureAlgorithm.HS256)
				.compact();
	}
	
	public String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails) {
		return Jwts.builder().setClaims(extraClaims)
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+604800000))
				.signWith(getSignKey(), SignatureAlgorithm.HS256)
				.compact();
	}
	
	private Key getSignKey() {
		byte[] key=Decoders.BASE64.decode("5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437");
		return Keys.hmacShaKeyFor(key);
	}
	
	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(getSignKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
	
	private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims=extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	

	
	public boolean isTokenValid(String token, UserDetails userDetails) {
		String username=extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	
	private boolean isTokenExpired(String token) {
		return extractClaim(token, Claims::getExpiration).before(new Date());
	}


	 
	 
    
	public String extractUsername(String token){
		 
            String decodedToken = new String(Base64.getDecoder().decode(token));
            String[] parts = decodedToken.split("\\|");
            
            String email = parts[0];
			return email;
	}
	
}
