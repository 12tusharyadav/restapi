package com.springboot.restApi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.springboot.restApi.model.JwtAuthResponse;
import com.springboot.restApi.model.JwtAuthRequest;

import com.springboot.restApi.servicesjwt.CustomUserDetailsService;
import com.springboot.restApi.servicesjwt.JwtTokenHelper;

// generat  token firt time 
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception
	{
		System.out.println(request+" ooooooo00000000000");
		System.out.println("tushar 12");
		this.authenticate(request.getUsername(),request.getPassword());
		UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(request.getUsername());
		String token = this.jwtTokenHelper.generateToken(userDetails);
		
		System.out.println("Your token isssssssssssssssssssss"+token);
		JwtAuthResponse response = new JwtAuthResponse();
		response.setToken(token);
		return new ResponseEntity<JwtAuthResponse>(response,HttpStatus.OK);
		
		
	}


	private void authenticate(String username, String password) throws Exception {
		// TODO Auto-generated method stub
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username,password);
		try {
		this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
		}catch(DisabledException e)
		{
			e.printStackTrace();
			throw new Exception("user is disabled");
		}
		
	}
	
}
