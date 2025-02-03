package dev.davletshin.gateway.domain.exception;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public class WebClientException extends RuntimeException {

    private final HttpStatusCode status;

    public WebClientException(String message, HttpStatusCode status) {
        super(message);
        this.status = status;
    }
}
