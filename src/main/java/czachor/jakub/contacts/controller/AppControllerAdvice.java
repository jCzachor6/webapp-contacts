package czachor.jakub.contacts.controller;

import czachor.jakub.contacts.service.exceptions.ContactDoesNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class AppControllerAdvice {
    @ExceptionHandler(value = ContactDoesNotExistException.class)
    public ResponseEntity contactDoesNotExistException(final ContactDoesNotExistException e){
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

}
