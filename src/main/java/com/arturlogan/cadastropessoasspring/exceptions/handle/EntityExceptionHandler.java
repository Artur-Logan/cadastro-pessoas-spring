package com.arturlogan.cadastropessoasspring.exceptions.handle;

import com.arturlogan.cadastropessoasspring.exceptions.PessoaNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class EntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardError>  handle(Exception e, HttpServletRequest request){
        StandardError standardError = new StandardError();

        standardError.setTimestamp(Instant.now());
        standardError.setStatus(HttpStatus.NOT_FOUND.value());
        standardError.setMessage(e.getMessage());
        standardError.setPath(request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(standardError);
    }

    @ExceptionHandler(PessoaNotFoundException.class)
    public ResponseEntity<StandardError> handle(PessoaNotFoundException e, HttpServletRequest request){
        StandardError standardError = new StandardError();

        standardError.setTimestamp(Instant.now());
        standardError.setStatus(HttpStatus.NOT_FOUND.value());
        standardError.setMessage(e.getMessage());
        standardError.setPath(request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(standardError);
    }
}
