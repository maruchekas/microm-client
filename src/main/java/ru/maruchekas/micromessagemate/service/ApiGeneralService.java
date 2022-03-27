package ru.maruchekas.micromessagemate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.maruchekas.micromessagemate.api.data.AuthData;
import ru.maruchekas.micromessagemate.api.data.MessageData;
import ru.maruchekas.micromessagemate.api.data.UserData;
import ru.maruchekas.micromessagemate.api.response.ConfirmLoginResponse;
import ru.maruchekas.micromessagemate.api.response.ConfirmPostMessage;
import ru.maruchekas.micromessagemate.api.response.ListMessagesDataResponse;
import ru.maruchekas.micromessagemate.appconfig.security.JwtGenerator;
import ru.maruchekas.micromessagemate.exception.AccessDeniedException;
import ru.maruchekas.micromessagemate.exception.CustomIllegalArgumentException;
import ru.maruchekas.micromessagemate.exception.IncorrectLoginPasswordPairException;
import ru.maruchekas.micromessagemate.exception.MessageNotFoundException;

import java.util.List;

@Service
public class ApiGeneralService {

    @Autowired
    private MicroMessageProxyService proxy;

    @Autowired
    JwtGenerator jwtGenerator;

    private static String jwtToken;

    public ConfirmPostMessage postMessage(MessageData messageData) {
        MessageData response = proxy.postMessageData(jwtToken, messageData);
        return new ConfirmPostMessage()
                .setId(response.getId())
                .setText(response.getText())
                .setCreatedTime(response.getCreatedTime());
    }

    public MessageData getMessage(Long id) throws MessageNotFoundException {
        MessageData response;
        try {
            response = proxy.returnMessageData(jwtToken, id);
        } catch (Exception e) {
            throw new MessageNotFoundException();
        }
        return new MessageData(response.getId(), response.getText(), response.getCreatedTime());
    }

    public ListMessagesDataResponse getMessageListByRange(String from, String to) throws CustomIllegalArgumentException {
        ListMessagesDataResponse response;
        try {
            response = proxy.returnMessageList(jwtToken, from, to);
        } catch (Exception e) {
            throw new CustomIllegalArgumentException();
        }
        return new ListMessagesDataResponse()
                .setTotal(response.getTotal())
                .setMessageList(response.getMessageList());
    }

    public List<UserData> getAllUsers() throws AccessDeniedException {
        List<UserData> users;
        try {
            users = proxy.getAllUsers(jwtToken);
        } catch (Exception e) {
            throw new AccessDeniedException();
        }
        return users;
    }

    public ConfirmLoginResponse loginUser(AuthData authData) throws IncorrectLoginPasswordPairException {
        ConfirmLoginResponse response;
        try {
            response = proxy.login(authData);
        } catch (Exception e) {
            throw new IncorrectLoginPasswordPairException();
        }
        jwtToken = jwtGenerator.generateToken(authData.getEmail());
        return response;
    }
}
