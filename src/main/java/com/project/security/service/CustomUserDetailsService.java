package com.project.security.service;
//we have created this service class in service package to implement user details service interface and check against db
import java.util.Objects;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.project.security.CustomUserDetails;
import com.project.security.entity.User;
import com.project.security.repository.UserRepository;

@Component
public class CustomUserDetailsService implements UserDetailsService{
	
	private final UserRepository userRepository;
	
	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUserName(username);
		if(Objects.isNull(user)) {
			System.out.println("User not available");
			
		}
		return new CustomUserDetails(user);
	}

}
