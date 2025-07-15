package com.example.Pahana.Edu.controller;

import com.example.Pahana.Edu.model.Customer;
import com.example.Pahana.Edu.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.Pahana.Edu.security.JwtUtil;
import java.util.Optional;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Customer customer) {
        if (customerRepository.findByUsername(customer.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists");
        }
        Customer saved = customerRepository.save(customer);
        return ResponseEntity.ok(saved);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> payload) {
        String username = payload.get("username");
        String password = payload.get("password");

        Optional<Customer> optCustomer = customerRepository.findByUsername(username);

        if (optCustomer.isPresent() && optCustomer.get().getPassword().equals(password)) {
            String token = JwtUtil.generateToken(username);
            return ResponseEntity.ok(Map.of(
                    "token", token,
                    "customer", optCustomer.get()
            ));
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }


}