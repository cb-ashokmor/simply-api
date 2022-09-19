package org.simply.api.service.exception;

import org.simply.api.common.model.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionAdvice {

    @ExceptionHandler(NoObjectFoundException.class)
    public ResponseEntity<Error> noObjectFoundException(NoObjectFoundException ex) {
        return new ResponseEntity<>(ex.getError(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Error> illegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(ex.getError(), HttpStatus.BAD_REQUEST);
    }

}