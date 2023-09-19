package com.example.phone_ordering_webapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.phone_ordering_webapp.model.Customer;
 
@Repository
public interface CustomerRepo extends JpaRepository<Customer,Integer>{

}
