package com.cristian.shortin_api.infra.exception;

import jakarta.annotation.Nullable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UrlNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleUrlNotFoundException(UrlNotFoundException ex) {
        ErrorMessage message = new ErrorMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    @ExceptionHandler(MalformedURLException.class)
    public ResponseEntity<ErrorMessage> handleMalformedURLException(Exception ex) {
        ErrorMessage message = new ErrorMessage(ex.getCause().getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @Override
    protected @Nullable ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var errorList = generateErrorList(ex.getBindingResult());
        return new ResponseEntity<>(errorList, headers, HttpStatus.BAD_REQUEST);
    }

    private List<ErrorMessage> generateErrorList(BindingResult bindingResult) {
        List<ErrorMessage> errorList = new ArrayList<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorList.add(new ErrorMessage(fieldError.getDefaultMessage()));
        }
        return errorList;
    }

    public record ErrorMessage(String message) {
    }
}

