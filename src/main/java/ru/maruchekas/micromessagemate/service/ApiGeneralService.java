package ru.maruchekas.micromessagemate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.maruchekas.micromessagemate.appconfig.security.JwtGenerator;
import ru.maruchekas.micromessagemate.data.AuthData;
import ru.maruchekas.micromessagemate.data.MessageData;
import ru.maruchekas.micromessagemate.data.UserData;
import ru.maruchekas.micromessagemate.exception.CustomIllegalArgumentException;
import ru.maruchekas.micromessagemate.response.ConfirmLoginResponse;
import ru.maruchekas.micromessagemate.response.ConfirmPostMessage;
import ru.maruchekas.micromessagemate.response.ListMessagesDataResponse;

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

    public MessageData getMessage(Long id){
        MessageData response = proxy.returnMessageData(jwtToken, id);
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

    public List<UserData> getAllUsers(){
        return proxy.getAllUsers(jwtToken);
    }

    public ConfirmLoginResponse loginUser(AuthData authData){

        jwtToken = jwtGenerator.generateToken(authData.getEmail());
        return proxy.login(authData);
    }
}
