package ru.maruchekas.micromessagemate.exception;

import static ru.maruchekas.micromessagemate.appconfig.Constants.ACCESS_DENIED;

public class AccessDeniedException extends Exception{

    public AccessDeniedException() {
        super(ACCESS_DENIED);
    }
}
