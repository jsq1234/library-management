package library.backend.api.controllers;

import library.backend.api.models.Book;
import library.backend.api.models.Fine;
import library.backend.api.models.Issue;
import library.backend.api.repositories.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
public class FineController {

    @Autowired
    private IssueRepository issueRepository;
    @GetMapping(path = "/fine/{issue_id}")
    public ResponseEntity<Fine> getFine(@PathVariable Long issue_id){
//        LocalDate d1 = LocalDate.now();
//        LocalDate d2 = d1.minusDays(2);
//        return ResponseEntity.ok(new Fine(1L,123L,"Sherlock Holmes", d2, d1, 200));

        Issue issued_book = issueRepository.findById(issue_id).orElse(null);
        if(issued_book == null) return ResponseEntity.notFound().build();

        Book book = issued_book.getBook();
        LocalDate issueDate = issued_book.getIssueDate();
        LocalDate returnDate = issued_book.getReturnDate();
        Integer issuePeriod = issued_book.getIssuePeriod();

        if(returnDate.minusDays(issuePeriod).compareTo(issueDate)>0) return ResponseEntity.ok(new Fine(book.getId(),book.getTitle(), issueDate, returnDate, 100));

        return ResponseEntity.ok(new Fine(book.getId(),book.getTitle(), issueDate, returnDate, 0));

    }

}
