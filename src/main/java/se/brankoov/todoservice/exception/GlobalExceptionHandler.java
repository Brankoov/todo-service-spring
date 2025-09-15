package se.brankoov.todoservice.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

  public record ApiError(Instant timestamp, int status, String error, String message, String path) {}

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
    var msg = ex.getBindingResult().getFieldErrors().stream()
            .map(e -> e.getField() + ": " + e.getDefaultMessage())
            .collect(Collectors.joining("; "));
    var body = new ApiError(Instant.now(), 400, "Bad Request", msg, req.getRequestURI());
    return ResponseEntity.badRequest().body(body);
  }

  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<ApiError> handleResponseStatus(ResponseStatusException ex, HttpServletRequest req) {
    int status = ex.getStatusCode().value();
    var body = new ApiError(
            Instant.now(),
            status,
            ex.getStatusCode().toString(), // t.ex. "404 NOT_FOUND"
            ex.getReason(),
            req.getRequestURI()
    );
    return ResponseEntity.status(ex.getStatusCode()).body(body);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiError> handleGeneric(Exception ex, HttpServletRequest req) {
    var body = new ApiError(Instant.now(), 500, "Internal Server Error", "Unexpected error", req.getRequestURI());
    return ResponseEntity.status(500).body(body);
  }
}
