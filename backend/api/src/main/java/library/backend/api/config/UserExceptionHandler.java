package library.backend.api.config;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolationException;
import library.backend.api.dto.ApiError;
import library.backend.api.dto.ValidationError;
import library.backend.api.exceptions.MissingLoginFieldsException;
import library.backend.api.exceptions.UserAlreadyExistsException;

@RestControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(MissingLoginFieldsException.class)
    public ResponseEntity<ApiError> handleMissingLoginFieldsException(
            MissingLoginFieldsException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleUserExistsException(UserAlreadyExistsException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ApiError(HttpStatus.CONFLICT, ex.getMessage()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ValidationError> handleConstriantViolations(ConstraintViolationException ex) {
        var errMap = new HashMap<String, String>();
        ex.getConstraintViolations().stream()
                .forEach(err -> errMap.put(err.getPropertyPath().toString(), err.getMessage()));

        var error = new ValidationError(HttpStatus.BAD_REQUEST, errMap);
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> handleConstriantViolation(MethodArgumentNotValidException ex) {
        var errMap = new HashMap<String, String>();
        ex.getBindingResult().getFieldErrors().stream()
                .forEach(err -> errMap.put(err.getField(), err.getDefaultMessage()));
        var error = new ValidationError(HttpStatus.BAD_REQUEST, errMap);
        return ResponseEntity.badRequest().body(error);
    }
}
