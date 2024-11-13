package dev.davletshin.calculator.domain.exception;

public class RefuseException extends RuntimeException {
    public RefuseException(String message) {
        super(message);
    }
}
