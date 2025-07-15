package com.example.Pahana.Edu.repository;

import com.example.Pahana.Edu.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {
    List<Order> findByCustomerContact(String customerContact);
}