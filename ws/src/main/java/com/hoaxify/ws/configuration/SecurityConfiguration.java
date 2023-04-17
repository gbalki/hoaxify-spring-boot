package com.hoaxify.ws.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;



@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter  {

	@Autowired
	UserAuthService userAuthService;
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable(); // for close the token
		
		http.httpBasic().authenticationEntryPoint(new AuthEntryPoint()); // for close the pop up input
		
		http.authorizeRequests()
		.antMatchers(HttpMethod.POST, "/api/1.0/auth").authenticated()
		.antMatchers(HttpMethod.PUT, "/api/1.0/users/{username}").authenticated()
		.antMatchers(HttpMethod.POST, "/api/1.0/hoaxes").authenticated()
		.and()
		.authorizeRequests().anyRequest().permitAll();
		
		http.headers().frameOptions().disable();
		
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // for spring security to disable session management in
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userAuthService).passwordEncoder(passwordEncoder());
	}	
	//This method is to encode the password 
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
