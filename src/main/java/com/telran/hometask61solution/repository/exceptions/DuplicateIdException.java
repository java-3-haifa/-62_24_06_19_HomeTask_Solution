package com.telran.hometask61solution.repository.exceptions;

public class DuplicateIdException extends RepositoryException {
    public DuplicateIdException(String message) {
        super(message);
    }

    public DuplicateIdException(String message, Throwable cause) {
        super(message, cause);
    }
}
