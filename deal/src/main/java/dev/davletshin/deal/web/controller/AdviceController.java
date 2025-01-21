package dev.davletshin.deal.web.controller;

import dev.davletshin.calculator.domain.exception.ExceptionBody;
import dev.davletshin.calculator.domain.exception.RefuseException;
import dev.davletshin.deal.domain.exception.ResourceNotFoundException;
import dev.davletshin.deal.domain.exception.SesCodeNotConfirmed;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class AdviceController {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionBody handleResourceNotFoundException(ResourceNotFoundException e) {
        log.error(e.getMessage());
        return new ExceptionBody(e.getMessage());
    }

    @ExceptionHandler(SesCodeNotConfirmed.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionBody sesCodeNotConfirmedException(ResourceNotFoundException e) {
        log.error(e.getMessage());
        return new ExceptionBody(e.getMessage());
    }

    @ExceptionHandler(RefuseException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionBody handleResourceNotFound(
            final RefuseException e
    ) {
        log.error(e.getMessage());
        return new ExceptionBody(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleMethodArgumentNotValid(
            final MethodArgumentNotValidException e
    ) {
        ExceptionBody exceptionBody = new ExceptionBody("Validation failed.");
        List<FieldError> errors = e.getBindingResult().getFieldErrors();
        exceptionBody.setErrors(errors.stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage,
                        (existingMessage, newMessage) ->
                                existingMessage + " " + newMessage)
                ));
        log.error(exceptionBody.getMessage());
        return exceptionBody;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleConstraintViolation(
            final ConstraintViolationException e
    ) {
        ExceptionBody exceptionBody = new ExceptionBody("Validation failed.");
        exceptionBody.setErrors(e.getConstraintViolations().stream()
                .collect(Collectors.toMap(
                        violation -> violation.getPropertyPath().toString(),
                        ConstraintViolation::getMessage
                )));
        log.error(exceptionBody.getMessage());
        return exceptionBody;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionBody handleException(
            final Exception e
    ) {
        log.error(e.getMessage());
        return new ExceptionBody("Internal error.");
    }
}
