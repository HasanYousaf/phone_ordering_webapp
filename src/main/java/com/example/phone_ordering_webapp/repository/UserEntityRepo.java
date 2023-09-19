package com.example.phone_ordering_webapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.phone_ordering_webapp.model.Customer;
import com.example.phone_ordering_webapp.model.Receipt;
import com.example.phone_ordering_webapp.model.UserEntity;

@Repository
public interface UserEntityRepo extends JpaRepository<UserEntity,Integer>{

	UserEntity findUserEntityByEmail(String email);
	
	

}