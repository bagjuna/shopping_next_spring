package org.zerock.apiserver.todo.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.zerock.apiserver.todo.exceptions.TodoNotFoundException;

@RestControllerAdvice
public class TodoAPIAdvice {

    @ExceptionHandler(TodoNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(TodoNotFoundException ex) {
        return errorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), System.currentTimeMillis());
    }

    // 다른 예외들을 처리하는 @ExceptionHandler 메서드들 추가 가능
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
        return errorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), System.currentTimeMillis());
    }
    
    private ResponseEntity<Object> errorResponse(HttpStatus status, String message, long timestamp) {

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, message);
        problemDetail.setTitle(message);
        problemDetail.setProperty("timestamp", System.currentTimeMillis()); // 추가 속성
        return ResponseEntity.of(problemDetail).build();
        
    }
    
}
