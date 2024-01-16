package ch.rishabh.sverwaltung.controller;

import ch.rishabh.sverwaltung.service.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({StudentNotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(
            Exception ex, WebRequest request) {
        if (ex instanceof StudentNotFoundException) {
            StudentNotFoundException hex = (StudentNotFoundException) ex;
            return new ResponseEntity<Object>(String.format("Student with id '%s' not found", hex.getId()), new HttpHeaders(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Object>(
                "Student not found", new HttpHeaders(), HttpStatus.NOT_FOUND);
    }
}
