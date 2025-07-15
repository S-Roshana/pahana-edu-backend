package com.example.Pahana.Edu.controller;

import com.example.Pahana.Edu.model.Book;
import com.example.Pahana.Edu.model.Customer;
import com.example.Pahana.Edu.service.BookService;
import com.example.Pahana.Edu.repository.CustomerRepository;
import com.example.Pahana.Edu.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "*")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.findAll();
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        try {
            // Initialize userRatings as empty HashMap
            if (book.getUserRatings() == null) {
                book.setUserRatings(new HashMap<>());
            }

            Book savedBook = bookService.save(book);
            return ResponseEntity.ok(savedBook);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable String id) {
        return bookService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable String id) {
        bookService.deleteById(id);
    }

    @GetMapping("/category/{category}")
    public List<Book> getBooksByCategory(@PathVariable String category) {
        return bookService.findByCategory(category);
    }

    @PutMapping("/{id}/rating")
    public ResponseEntity<?> updateRating(@PathVariable String id,
                                          @RequestBody Map<String, Object> payload,
                                          @RequestHeader("Authorization") String authHeader) {
        try {
            // Extract JWT token
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(401).body("Authorization header missing or invalid");
            }

            String token = authHeader.substring(7);
            String username = JwtUtil.extractUsername(token);

            // Validate token
            if (!JwtUtil.validateToken(token, username)) {
                return ResponseEntity.status(401).body("Invalid or expired token");
            }

            // Get user ID from database
            Optional<Customer> customer = customerRepository.findByUsername(username);
            if (!customer.isPresent()) {
                return ResponseEntity.status(404).body("User not found");
            }

            double rating = ((Number) payload.get("rating")).doubleValue();
            if (rating < 0.5 || rating > 5.0) {
                return ResponseEntity.status(400).body("Rating must be between 0.5 and 5.0");
            }

            Book updatedBook = bookService.updateUserRating(id, customer.get().getId(), rating);
            return ResponseEntity.ok(updatedBook);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating rating: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}/rating")
    public ResponseEntity<?> deleteRating(@PathVariable String id,
                                          @RequestHeader("Authorization") String authHeader) {
        try {
            // Extract and validate JWT token
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(401).body("Authorization header missing or invalid");
            }

            String token = authHeader.substring(7);
            String username = JwtUtil.extractUsername(token);

            if (!JwtUtil.validateToken(token, username)) {
                return ResponseEntity.status(401).body("Invalid or expired token");
            }

            // Get user ID
            Optional<Customer> customer = customerRepository.findByUsername(username);
            if (!customer.isPresent()) {
                return ResponseEntity.status(404).body("User not found");
            }

            Book updatedBook = bookService.removeUserRating(id, customer.get().getId());
            return ResponseEntity.ok(updatedBook);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting rating: " + e.getMessage());
        }
    }

    @GetMapping("/{id}/user-rating")
    public ResponseEntity<?> getUserRating(@PathVariable String id,
                                           @RequestHeader("Authorization") String authHeader) {
        try {
            // Extract and validate JWT token
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(401).body("Authorization header missing or invalid");
            }

            String token = authHeader.substring(7);
            String username = JwtUtil.extractUsername(token);

            if (!JwtUtil.validateToken(token, username)) {
                return ResponseEntity.status(401).body("Invalid or expired token");
            }

            // Get user ID
            Optional<Customer> customer = customerRepository.findByUsername(username);
            if (!customer.isPresent()) {
                return ResponseEntity.status(404).body("User not found");
            }

            Book book = bookService.findById(id);
            Double userRating = book.getUserRating(customer.get().getId());

            return ResponseEntity.ok(Map.of(
                    "hasRated", userRating != null,
                    "rating", userRating != null ? userRating : 0.0
            ));

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error getting user rating: " + e.getMessage());
        }
    }
}
