package com.example.Pahana.Edu.service;

import com.example.Pahana.Edu.model.Book;
import com.example.Pahana.Edu.model.Order;
import com.example.Pahana.Edu.repository.BookRepository;
import com.example.Pahana.Edu.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private BookRepository bookRepository;

    public String placeOrder(Order order) {
        Book book = bookRepository.findById(order.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        order.setBookTitle(book.getTitle());
        order.setTotalPrice(book.getPrice() * order.getQuantity());
        order.setOrderDate(LocalDateTime.now());

        orderRepository.save(order);
        return "Order placed successfully";
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}