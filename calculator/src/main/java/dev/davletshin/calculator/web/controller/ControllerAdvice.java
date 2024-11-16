package dev.davletshin.calculator.web.controller;

import dev.davletshin.calculator.domain.LogLevel;
import dev.davletshin.calculator.domain.exception.ExceptionBody;
import dev.davletshin.calculator.domain.exception.RefuseException;
import dev.davletshin.calculator.service.LogData;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerAdvice {

    private final LogData logData = LogData.getInstance();

    @ExceptionHandler(RefuseException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionBody handleResourceNotFound(
            final RefuseException e
    ) {
        logData.logInfo(e.getMessage(), null, LogLevel.ERROR);
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
        logData.logInfo(exceptionBody.getMessage(), null, LogLevel.ERROR);
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
        logData.logInfo(exceptionBody.getMessage(), null, LogLevel.ERROR);
        return exceptionBody;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionBody handleException(
            final Exception e
    ) {
        e.printStackTrace();
        logData.logInfo(e.getMessage(), null, LogLevel.ERROR);
        return new ExceptionBody("Internal error.");
    }
}
