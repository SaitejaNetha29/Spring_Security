package com.project.security.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	
	//to add security login when we hit api
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
		security
		.csrf(csrf -> csrf.disable()) // we can also disable csrf by using disable method
		.authorizeHttpRequests(
				request -> request
				.requestMatchers("/register","/login").permitAll()
				.anyRequest().authenticated()
				)
		.httpBasic(Customizer.withDefaults());
		return security.build();
	}
	
	//creating our own userdetails,to check the login authentication
	
	//@Bean
	public UserDetailsService userDetailsService() {
		
		UserDetails teja = User.withUsername("teja")
				.password("{noop}password") //noop is used to disable the password encoding(not in prod)
				.roles("USER")
				.build();
		
		UserDetails nikhil = User.withUsername("nikhil")
				.password("{noop}nikhil")
				.roles("USER")
				.build();
		
		return new InMemoryUserDetailsManager(teja,nikhil);
		
	}
	
	//creating authentication provider to handle user details directly from db
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() { // to encrypt the password
		return new BCryptPasswordEncoder(14);
	}
	
	
	@Bean 
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = 
				new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		//provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
		provider.setPasswordEncoder(bCryptPasswordEncoder());
		
		return provider;
	}
	
	@Bean
	public AuthenticationManager authenticationManager(
			AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

}
