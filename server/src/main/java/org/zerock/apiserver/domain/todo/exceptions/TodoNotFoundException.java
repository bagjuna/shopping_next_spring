package org.zerock.apiserver.domain.todo.exceptions;

public class TodoNotFoundException extends RuntimeException{

    public TodoNotFoundException(String message) {
        super(message);
    }
}
