package com.example.phone_ordering_webapp.service;

import org.springframework.stereotype.Service;

import com.example.phone_ordering_webapp.model.UserEntity;
import com.example.phone_ordering_webapp.repository.UserEntityRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserEntityRepo userEntityRepo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		 
		  UserEntity userEntity = userEntityRepo.findUserEntityByEmail(email);
		  
			UserBuilder  users = User.withUsername(email);
			UserDetails user = users
			           .username(userEntity.getEmail())
			           .password(userEntity.getPassword())
			           .roles(userEntity.getRole())
			           .build();
		return user;
	}
	
    public UserEntity save(UserEntity user) {
    	
    	return userEntityRepo.save(user);
    }
    

}
