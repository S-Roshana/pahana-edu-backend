package com.example.Pahana.Edu.controller;

import com.example.Pahana.Edu.model.Book;
import com.example.Pahana.Edu.model.Order;
import com.example.Pahana.Edu.repository.OrderRepository;
import com.example.Pahana.Edu.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private BookService bookService;

    @PostMapping
    public ResponseEntity<?> placeOrder(@RequestBody Order order) {
        try {
            Book book = bookService.findById(order.getBookId());

            if (book.getQuantity() < order.getQuantity()) {
                return ResponseEntity.badRequest().body("❌ Not enough stock available");
            }

            // Reduce quantity in stock
            bookService.reduceQuantity(book.getId(), order.getQuantity());

            // Fill in order details
            order.setBookTitle(book.getTitle());
            order.setTotalPrice(book.getPrice() * order.getQuantity());
            order.setOrderDate(LocalDateTime.now());

            Order savedOrder = orderRepository.save(order);
            return ResponseEntity.ok(savedOrder);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("❌ " + e.getMessage());
        }
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @GetMapping("/byCustomer/{contact}")
    public List<Order> getOrdersByCustomer(@PathVariable String contact) {
        return orderRepository.findByCustomerContact(contact);
    }
}
