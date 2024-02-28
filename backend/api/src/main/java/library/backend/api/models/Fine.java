package library.backend.api.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
