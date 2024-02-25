package com.example.demo.service;

import com.example.demo.model.Books;
import org.springframework.stereotype.Service;

import java.util.List;




public interface BooksService {
    List<Books> findAllBooks();

    Books placeOrder(String id);
}
