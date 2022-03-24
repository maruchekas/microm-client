package ru.maruchekas.micromessagemate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.maruchekas.micromessagemate.data.MessageData;
import ru.maruchekas.micromessagemate.exception.CustomIllegalArgumentException;
import ru.maruchekas.micromessagemate.response.ConfirmPostMessage;
import ru.maruchekas.micromessagemate.response.ListMessagesDataResponse;

@Service
public class MessageService {

    @Autowired
    private MicroMessageProxyService proxy;

    public ConfirmPostMessage postMessage(MessageData messageData) {
        MessageData response = proxy.putMessageData(messageData);
        return new ConfirmPostMessage()
                .setId(response.getId())
                .setText(response.getText())
                .setCreatedTime(response.getCreatedTime());
    }

    public ListMessagesDataResponse getMessageListByRange(String from, String to) throws CustomIllegalArgumentException {
        ListMessagesDataResponse response;
        try {
            response = proxy.returnMessageList(from, to);
        } catch (Exception e) {
            throw new CustomIllegalArgumentException();
        }
        return new ListMessagesDataResponse()
                .setTotal(response.getTotal())
                .setMessageList(response.getMessageList());
    }
}
