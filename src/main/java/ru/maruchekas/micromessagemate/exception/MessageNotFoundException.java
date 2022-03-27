package ru.maruchekas.micromessagemate.exception;

import static ru.maruchekas.micromessagemate.appconfig.Constants.MESSAGE_NOT_FOUND;

public class MessageNotFoundException extends Exception{

    public MessageNotFoundException() {
        super(MESSAGE_NOT_FOUND);
    }
}
