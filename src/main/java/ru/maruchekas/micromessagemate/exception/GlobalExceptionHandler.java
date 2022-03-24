package ru.maruchekas.micromessagemate.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static ru.maruchekas.micromessagemate.appconfig.Constants.INVALID_ARGUMENT_ERR;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    ResponseEntity<?> handleException(CustomIllegalArgumentException customIllegalArgumentException) {
        return new ResponseEntity<>(INVALID_ARGUMENT_ERR, HttpStatus.BAD_REQUEST);
    }
}