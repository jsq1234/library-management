package library.backend.api.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class Fine {
    Long id;
    Long book_id;
    String title;
    LocalDate issueDate;
    LocalDate returnDate;
    Integer fine;

    public Fine(Long book_id, String title, LocalDate issueDate, LocalDate returnDate, Integer fine){
        this.fine = fine;
        this.book_id = book_id;
        this.issueDate = issueDate;
        this.returnDate = returnDate;
        this.title = title;
    }
}
