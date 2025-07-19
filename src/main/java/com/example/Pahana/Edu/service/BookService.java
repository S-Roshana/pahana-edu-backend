package com.example.Pahana.Edu.service;

import com.example.Pahana.Edu.model.Book;
import com.example.Pahana.Edu.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public Book findById(String id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }

    public void deleteById(String id) {
        bookRepository.deleteById(id);
    }

    public List<Book> findByCategory(String category) {
        return bookRepository.findByCategoryIgnoreCase(category);
    }

    public Book updateUserRating(String bookId, String userId, double newRating) {
        Book book = findById(bookId);
        book.setUserRating(userId, newRating);
        return bookRepository.save(book);
    }

    public Book removeUserRating(String bookId, String userId) {
        Book book = findById(bookId);
        book.removeUserRating(userId);
        return bookRepository.save(book);
    }

    public Book updateBook(String id, Book updated) {
        Book book = findById(id);
        book.setTitle(updated.getTitle());
        book.setAuthor(updated.getAuthor());
        book.setDescription(updated.getDescription());
        book.setImageUrl(updated.getImageUrl());
        book.setPrice(updated.getPrice());
        book.setQuantity(updated.getQuantity());
        book.setCategory(updated.getCategory());
        return bookRepository.save(book);
    }


    public Book reduceQuantity(String id, int quantity) {
        Book book = findById(id);
        if (book.getQuantity() < quantity) {
            throw new RuntimeException("Not enough stock available");
        }
        book.setQuantity(book.getQuantity() - quantity);
        return bookRepository.save(book);
    }
}
