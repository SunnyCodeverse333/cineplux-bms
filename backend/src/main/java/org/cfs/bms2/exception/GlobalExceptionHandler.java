package org.cfs.bms2.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String,Object>> handleRuntimeException(RuntimeException ex){
        Map<String,Object> error = new HashMap<>();
        error.put("timestamp" , LocalDateTime.now());
        error.put("message", ex.getMessage());
        error.put("status", HttpStatus.BAD_REQUEST.value());
//        new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,Object>> handleRuntimeException(Exception ex){
        Map<String,Object> error = new HashMap<>();
        error.put("timestamp" , LocalDateTime.now());
        error.put("message something went wrong ", ex.getMessage());
        error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
//        new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        return ResponseEntity.badRequest().body(error);
    }
}

/*public class ResponseEntity<T> {

    private T body;
    private HttpStatus status;

    // constructor
    public ResponseEntity(T body, HttpStatus status) {
        this.body = body;
        this.status = status;
    }

    // 🔥 THIS is what you're asking about
    public static BodyBuilder badRequest() {
        return new BodyBuilder(HttpStatus.BAD_REQUEST);
    }
}class BodyBuilder {

    private HttpStatus status;

    public BodyBuilder(HttpStatus status) {
        this.status = status;
    }

    public <T> ResponseEntity<T> body(T data) {
        return new ResponseEntity<>(data, status);
    }
}*/

/*public enum HttpStatus {
    OK(200),
    BAD_REQUEST(400),
    NOT_FOUND(404),
    INTERNAL_SERVER_ERROR(500);

    private int value;

    HttpStatus(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }
}*/