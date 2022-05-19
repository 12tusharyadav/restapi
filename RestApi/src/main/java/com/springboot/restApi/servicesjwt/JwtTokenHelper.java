package com.springboot.restApi.servicesjwt;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenHelper {

	
	public static final long  JWT_TOKEN_VALIDITY=  5 * 60 * 60;
	private String secret="jwtTokenKey";
	
	// retrive username from jwt token
	public String getUsernameFromToken(String token)
	{
		return getClaimFromToken(token,Claims::getSubject);
	}

	//retrive expirqation date from Token
	public Date getExpirationDateFromToken(String token)
	{
		return getClaimFromToken(token,Claims::getExpiration);
	}
	//
	public <T> T getClaimFromToken(String token,Function<Claims,T> claimsResolver)
	{
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}
	private Claims getAllClaimsFromToken(String token) {
		// TODO Auto-generated method stub
		  return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	//check if the token has expired
	  private Boolean isTokenExpired(String token) {
		  Date expiration = getExpirationDateFromToken(token);
	        return expiration.before(new Date());
	    }
	  //generate token for user
	  
	  public String generateToken(UserDetails userDetails) {
	        Map<String, Object> claims = new HashMap<>();
	        String token= doGenerateToken(claims, userDetails.getUsername());
	        System.out.println("Token Token Token Token Token"+token);
	        return token;
	    }
	  
	  private String doGenerateToken(Map<String, Object> claims, String subject) {

	        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
	                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000 * 60 * 60 * 10))
	                .signWith(SignatureAlgorithm.HS512, secret).compact();
	    }
	  
	  public Boolean validateToken(String token, UserDetails userDetails) {
	        final String username = getUsernameFromToken(token);
	        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	    }
	
	
}
