package ru.maruchekas.micromessagemate.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.maruchekas.micromessagemate.api.response.ExceptResponse;

import static ru.maruchekas.micromessagemate.appconfig.Constants.*;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    ResponseEntity<ExceptResponse> handleException(CustomIllegalArgumentException customIllegalArgumentException) {
        return new ResponseEntity<>(new ExceptResponse("date_format", INVALID_ARGUMENT_ERR), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    ResponseEntity<ExceptResponse> handleException(IncorrectLoginPasswordPairException incorrectLoginPasswordPairException) {
        return new ResponseEntity<>(new ExceptResponse("user", INCORRECT_LOGIN_OR_PASSWORD), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    ResponseEntity<ExceptResponse> handleException(MessageNotFoundException messageNotFoundException) {
        return new ResponseEntity<>(new ExceptResponse("message", MESSAGE_NOT_FOUND), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    ResponseEntity<ExceptResponse> handleException(AccessDeniedException accessDeniedException) {
        return new ResponseEntity<>(new ExceptResponse("access", ACCESS_DENIED), HttpStatus.FORBIDDEN);
    }
}