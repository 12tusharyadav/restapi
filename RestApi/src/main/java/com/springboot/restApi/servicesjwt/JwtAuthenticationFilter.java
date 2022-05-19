package com.springboot.restApi.servicesjwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		//1.gettoken
		
		String requestToken = request.getHeader("Authorization");
		//Bearer token mil jaygaa
		System.out.println("OKKKKKKKKKKKKKKKKKKKKKKKKK"+requestToken);
		
		String username=null;
		String token = null;
		
		if(requestToken!=null && requestToken.startsWith("Bearer "))
		{
			 token = requestToken.substring(7);
			System.out.println(token);
			try {
		     username = this.jwtTokenHelper.getUsernameFromToken(token);
		     System.out.println("username "+username);
			}catch(IllegalArgumentException e)
			{
				System.out.println("unable to get jwt token");
				e.printStackTrace();
			}catch(ExpiredJwtException e)
			{
				System.out.println("jwt token has expired");
				e.printStackTrace();
			}catch(MalformedJwtException e)
			{
				System.out.println("Invalid Jwt");
				e.printStackTrace();
			}
		}else
		{
			System.out.println("jwt token does b=not begin with bearer");
		}
		
		// once we get the token now validate 
		
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
		{
			UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(username);
			if(this.jwtTokenHelper.validateToken(token, userDetails))
			{
				// shi chal rha h
				// authentication krna h
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				
			}else
			{
				System.out.println("invalid jwt token");
			}
			
		}else
		{
			System.out.println("username is  null or context is not null ");
		}
		
		filterChain.doFilter(request, response);
		
	}

}
