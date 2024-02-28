package library.backend.api.repositories;

import library.backend.api.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import library.backend.api.models.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("SELECT b FROM Book b WHERE b.title = ?1")
    List<Book> findByBookName(String book);
}
