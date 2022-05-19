package com.springboot.restApi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.springboot.restApi.servicesjwt.CustomUserDetailsService;
import com.springboot.restApi.servicesjwt.JwtAuthenticationEntryPoint;
import com.springboot.restApi.servicesjwt.JwtAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class MysecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
	}
	

	@Override
	protected void configure(HttpSecurity http) throws Exception {

//		http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN").antMatchers("/user/**").hasRole("USER")
//				.antMatchers("/**").permitAll().and().formLogin().loginPage("/signin").loginProcessingUrl("/doLogin")
//				.defaultSuccessUrl("/user/index").and().csrf().disable();
				
		http.csrf().disable().authorizeHttpRequests().antMatchers("/api/v1/auth/login").permitAll()
		
		.anyRequest().authenticated().and().exceptionHandling()
		.authenticationEntryPoint(this.jwtAuthenticationEntryPoint).and().sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception
	{
		return super.authenticationManagerBean();
	}

}
