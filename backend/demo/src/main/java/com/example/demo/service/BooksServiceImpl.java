package com.example.demo.service;

import com.example.demo.model.Books;
import com.example.demo.repository.BooksRepository;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class BooksServiceImpl implements BooksService{
    private BooksRepository booksRepository;

    public BooksServiceImpl(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    @Override
    public List<Books> findAllBooks() {
        List<Books> books = booksRepository.findAll();

        return books;

    }

    @Override
    public Books placeOrder(String id) {

        Optional<Books> books = booksRepository.findById(id);
        System.out.println(books);
        return booksRepository.findById(id)
                .map(book -> {
                    book.setQuantity(book.getQuantity() - 1);
                    return booksRepository.save(book);
                })
                .orElseThrow();
    }


}
