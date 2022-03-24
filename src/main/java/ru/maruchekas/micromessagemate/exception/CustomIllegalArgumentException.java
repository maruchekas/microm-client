package ru.maruchekas.micromessagemate.exception;

import static ru.maruchekas.micromessagemate.appconfig.Constants.INVALID_ARGUMENT_ERR;

public class CustomIllegalArgumentException extends Exception{
    public CustomIllegalArgumentException() {
        super(INVALID_ARGUMENT_ERR);
    }
}
