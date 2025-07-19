package com.example.Pahana.Edu.repository;

import com.example.Pahana.Edu.model.Bill;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends MongoRepository<Bill, String> {
} 