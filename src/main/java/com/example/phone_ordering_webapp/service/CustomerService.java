package com.example.phone_ordering_webapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.phone_ordering_webapp.model.Customer;
import com.example.phone_ordering_webapp.repository.CustomerRepo;


 

@Service
public class CustomerService {
    @Autowired
	private CustomerRepo repository;
    
    
    public Customer saveCx(Customer cx) {
    	
    	return repository.save(cx);
    }
    
    
    public List<Customer> getCxs(){
    	
    	return repository.findAll();	
    }
    
    public Customer getCustomerByEmail(String email) {
    	List<Customer> cxs = getCxs();
    	Customer found = new Customer();
    	for (Customer cx: cxs) {
    		if (cx.getEmail() == email)
    			found = cx;
    	}
    	return found;
    	
    }
    
    public String getCustomerName(String email) {
    	List<Customer> cxs = getCxs();
    	String name = null;
    	for (Customer cx: cxs) {
    		if (cx.getEmail() == email)
    			name = cx.getName();
    	}
    	return name;
    }
    
    
}
