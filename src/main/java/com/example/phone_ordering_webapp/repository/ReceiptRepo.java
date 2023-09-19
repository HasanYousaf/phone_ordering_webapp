package com.example.phone_ordering_webapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.phone_ordering_webapp.model.Receipt;
 
@Repository
public interface ReceiptRepo extends JpaRepository<Receipt,Integer>{

}
