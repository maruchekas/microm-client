package ru.maruchekas.micromessagemate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.maruchekas.micromessagemate.data.AuthData;
import ru.maruchekas.micromessagemate.data.MessageData;
import ru.maruchekas.micromessagemate.data.UserData;
import ru.maruchekas.micromessagemate.exception.CustomIllegalArgumentException;
import ru.maruchekas.micromessagemate.response.ConfirmLoginResponse;
import ru.maruchekas.micromessagemate.response.ConfirmPostMessage;
import ru.maruchekas.micromessagemate.response.ListMessagesDataResponse;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MicroMessageProxyService proxy;
    private static String token;

    public ConfirmPostMessage postMessage(MessageData messageData) {
        MessageData response = proxy.postMessageData(token, messageData);
        return new ConfirmPostMessage()
                .setId(response.getId())
                .setText(response.getText())
                .setCreatedTime(response.getCreatedTime());
    }

    public MessageData getMessage(Long id){
        MessageData response = proxy.returnMessageData(token, id);
        return new MessageData(response.getId(), response.getText(), response.getCreatedTime());
    }

    public ListMessagesDataResponse getMessageListByRange(String from, String to) throws CustomIllegalArgumentException {
        ListMessagesDataResponse response;
        try {
            response = proxy.returnMessageList(token, from, to);
        } catch (Exception e) {
            throw new CustomIllegalArgumentException();
        }
        return new ListMessagesDataResponse()
                .setTotal(response.getTotal())
                .setMessageList(response.getMessageList());
    }

    public List<UserData> getAllUsers(){
        return proxy.getAllUsers(token);
    }

    public ConfirmLoginResponse loginUser(AuthData authData){

        ConfirmLoginResponse response = proxy.login(authData);
        token = response.getToken();
        return proxy.login(authData);
    }
}
