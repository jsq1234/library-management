package library.backend.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;

import java.util.HashMap;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class ValidationError {
    private HttpStatus status;
    private HashMap<String, String> errors;
}
