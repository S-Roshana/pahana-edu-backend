package com.example.Pahana.Edu.repository;

import com.example.Pahana.Edu.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {
    List<Book> findByCategoryIgnoreCase(String category);
}
