package com.springboot.restApi.model;

public class JwtAuthResponse {

	String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public JwtAuthResponse(String token) {
		super();
		this.token = token;
	}

	public JwtAuthResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "JwtAuthResponse [token=" + token + "]";
	}


}
