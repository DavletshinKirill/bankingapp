package dev.davletshin.gateway.domain.exception;

public class RefuseException extends RuntimeException {
    public RefuseException(String message) {
        super(message);
    }
}
