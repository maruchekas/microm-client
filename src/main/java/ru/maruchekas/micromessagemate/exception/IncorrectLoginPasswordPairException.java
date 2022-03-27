package ru.maruchekas.micromessagemate.exception;

import static ru.maruchekas.micromessagemate.appconfig.Constants.INCORRECT_LOGIN_OR_PASSWORD;

public class IncorrectLoginPasswordPairException extends Exception{
    public IncorrectLoginPasswordPairException() {
        super(INCORRECT_LOGIN_OR_PASSWORD);
    }
}
