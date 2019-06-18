package com.telran.hometask61solution.repository.exceptions;

public class IllegalIdException extends RepositoryException {
    public IllegalIdException(String message) {
        super(message);
    }

    public IllegalIdException(String message, Throwable cause) {
        super(message, cause);
    }
}
