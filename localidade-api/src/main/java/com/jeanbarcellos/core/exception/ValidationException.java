package com.jeanbarcellos.core.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationException extends ApplicationException {

    private final List<String> errors;

    public ValidationException(String message) {
        super(message);
        this.errors = new ArrayList<>();
    }

    public ValidationException(String message, List<String> errors) {
        super(message);
        this.errors = errors;
    }

    public List<String> getErrors() {
        return this.errors;
    }

    public boolean hasErrors() {
        return !this.errors.isEmpty();
    }

    public static ValidationException of(String message) {
        return new ValidationException(message);
    }

    public static ValidationException of(String message, List<String> errors) {
        return new ValidationException(message, errors);
    }

}
