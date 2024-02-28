package library.backend.api.controllers;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import library.backend.api.models.Book;
import library.backend.api.repositories.BookRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBooks(@PathVariable Long id) {
        Book book = bookRepository.findById(id).orElse(null);

        if (book == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(book);
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<List<Book>> getBooks(@PathVariable String title){
        List<Book> books =  bookRepository.findByBookName(title);
        if(books.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(books);
    }

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        Book savedBook = bookRepository.save(book);
        return new ResponseEntity<>(savedBook, HttpStatusCode.valueOf(200));
    }

    @PutMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Book> updateBook(@RequestBody Book book){
        Book savedBook = bookRepository.findById(book.getId()).orElse(null);
        if(savedBook == null) return ResponseEntity.notFound().build();
        savedBook.setAuthor(book.getAuthor());
        savedBook.setGenre(book.getGenre());
        savedBook.setISBN(book.getISBN());
        savedBook.setPrice(book.getPrice());
        savedBook.setPublishDate(book.getPublishDate());
        savedBook.setTitle(book.getTitle());
        bookRepository.save(savedBook);

        return ResponseEntity.ok(savedBook);
    }
}
