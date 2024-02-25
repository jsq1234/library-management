package com.example.demo.controller;


import com.example.demo.model.Books;
import com.example.demo.service.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class BooksController {

    private BooksService booksService;

    @Autowired
    public BooksController(BooksService booksService) {
        this.booksService = booksService;
    }


    @GetMapping("/books")
    public List<Books> getAllBooks(){
        List<Books>  books= booksService.findAllBooks();
        System.out.println(books);
        return books;
    }


    @PatchMapping("/books/{id}/placeOrder")
    public Books placeOrder(@PathVariable String id) {
        return booksService.placeOrder(id);
    }


}
