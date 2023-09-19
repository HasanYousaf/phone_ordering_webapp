package com.example.phone_ordering_webapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.stereotype.Service;

import com.example.phone_ordering_webapp.model.Receipt;
import com.example.phone_ordering_webapp.repository.ReceiptRepo;

@Service
public class ReceiptService {
    @Autowired
	private ReceiptRepo repository;
    
    
    public Receipt saveReceipt(Receipt receipt) {
    	
    	return repository.save(receipt);
    }
    
    
    public List<Receipt> getReceipts(){
    	
    	return repository.findAll();
    	
    }
    
    public List<Receipt> getOrdersByCx(String email) {
    	List<Receipt> match = new ArrayList<Receipt>();
    	for (Receipt receipt : getReceipts()) {
    		if (email == receipt.getEmail()) {
    			match.add(receipt);
    		}
    	}
    	return match;
    	}
    
    }
    
