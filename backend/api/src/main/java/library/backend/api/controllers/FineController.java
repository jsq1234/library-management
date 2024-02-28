package library.backend.api.controllers;

import library.backend.api.models.Fine;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
public class FineController {

    @GetMapping(path = "/fine")
    public ResponseEntity<Fine> getFine(){
//        LocalDate d1 = LocalDate.now();
//        LocalDate d2 = d1.minusDays(2);
//        return ResponseEntity.ok(new Fine(1L,123L,"Sherlock Holmes", d2, d1, 200));
    }

}
