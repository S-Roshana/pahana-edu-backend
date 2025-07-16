package com.example.Pahana.Edu.controller;

import com.example.Pahana.Edu.model.Customer;
import com.example.Pahana.Edu.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.Pahana.Edu.security.JwtUtil;
import java.util.Optional;

import java.util.Map;
import com.example.Pahana.Edu.model.Admin;
import com.example.Pahana.Edu.repository.AdminRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.Random;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AdminRepository adminRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private String generateUniqueAccountNumber() {
        Random random = new Random();
        while (true) {
            String accountNumber = String.format("%08d", random.nextInt(100_000_000));
            boolean exists = customerRepository.findAll().stream()
                .anyMatch(c -> accountNumber.equals(c.getAccountNumber()));
            if (!exists) return accountNumber;
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Customer customer) {
        if (customerRepository.findByUsername(customer.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists");
        }
        // Hash the password before saving
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        // Generate unique account number
        customer.setAccountNumber(generateUniqueAccountNumber());
        Customer saved = customerRepository.save(customer);
        return ResponseEntity.ok(saved);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> payload) {
        String username = payload.get("username");
        String password = payload.get("password");

        Optional<Customer> optCustomer = customerRepository.findByUsername(username);

        if (optCustomer.isPresent() && passwordEncoder.matches(password, optCustomer.get().getPassword()) && "customer".equals(optCustomer.get().getRole())) {
            String token = JwtUtil.generateToken(username, "customer");
            return ResponseEntity.ok(Map.of(
                    "token", token,
                    "role", "customer",
                    "customer", optCustomer.get()
            ));
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    @PostMapping("/admin/login")
    public ResponseEntity<?> adminLogin(@RequestBody Map<String, String> payload) {
        String username = payload.get("username");
        String password = payload.get("password");

        Optional<Admin> optAdmin = adminRepository.findByUsername(username);

        if (optAdmin.isPresent() && optAdmin.get().getPassword().equals(password) && "admin".equals(optAdmin.get().getRole())) {
            String token = JwtUtil.generateToken(username, "admin");
            return ResponseEntity.ok(Map.of(
                    "token", token,
                    "role", "admin",
                    "admin", optAdmin.get()
            ));
        } else {
            return ResponseEntity.status(401).body("Invalid admin credentials");
        }
    }


}