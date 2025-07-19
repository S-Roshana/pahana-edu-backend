package com.example.Pahana.Edu.controller;

import com.example.Pahana.Edu.model.Customer;
import com.example.Pahana.Edu.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomer(@PathVariable String id) {
        return customerRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable String id, @RequestBody Customer updated) {
        return customerRepository.findById(id)
                .map(c -> {
                    c.setName(updated.getName());
                    c.setContactNo(updated.getContactNo());
                    c.setAddress(updated.getAddress());
                    c.setUsername(updated.getUsername());
                    c.setPassword(updated.getPassword());
                    // Preserve role and accountNumber
                    // (do not update from 'updated', keep existing values)
                    Customer saved = customerRepository.save(c);
                    return ResponseEntity.ok(saved);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<?> activateCustomer(@PathVariable String id) {
        return customerRepository.findById(id)
                .map(c -> {
                    c.setAccountStatus("ACTIVE");
                    Customer saved = customerRepository.save(c);
                    return ResponseEntity.ok(saved);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<?> deactivateCustomer(@PathVariable String id) {
        return customerRepository.findById(id)
                .map(c -> {
                    c.setAccountStatus("INACTIVE");
                    Customer saved = customerRepository.save(c);
                    return ResponseEntity.ok(saved);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
