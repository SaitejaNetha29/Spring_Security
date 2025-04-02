package com.project.security.service;

import java.net.Authenticator;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.security.entity.User;
import com.project.security.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bcrypt;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	

	public User register(User user) {
		user.setPassword(bcrypt
				.encode(user.getPassword()));
		return userRepository.save(user);
	}

	public String verify(User user) {
		Authentication authenticate =  authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
		if(authenticate.isAuthenticated()) {
			return "2432523465fdgdd365767566887687";
		}
		return "failure";
	}

}
