package library.backend.api.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import library.backend.api.dto.ErrorDto;
import library.backend.api.exceptions.MissingLoginFieldsException;

@RestControllerAdvice
public class AuthServiceExceptionHandler {

    @ExceptionHandler(MissingLoginFieldsException.class)
    public ResponseEntity<ErrorDto> handleMissingLoginFieldsException(
            MissingLoginFieldsException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorDto(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

}
