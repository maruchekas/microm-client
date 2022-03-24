package ru.maruchekas.micromessagemate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.maruchekas.micromessagemate.response.ListMessagesDataResponse;
import ru.maruchekas.micromessagemate.data.MessageData;
import ru.maruchekas.micromessagemate.exception.CustomIllegalArgumentException;
import ru.maruchekas.micromessagemate.response.ConfirmPostMessage;
import ru.maruchekas.micromessagemate.service.MessageService;
import ru.maruchekas.micromessagemate.service.MicroMessageProxyService;

@RestController
@RequestMapping("/api")
public class MessageController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private MicroMessageProxyService proxy;

    @Autowired
    MessageService messageService;

    @GetMapping("/message/{id}")
    public MessageData getMessage(@PathVariable("id") Long id) {
        MessageData response = proxy.returnMessageData(id);
        logger.info("{}", response);
        return new MessageData(response.getId(), response.getText(), response.getCreatedTime());
    }

    @GetMapping("/message/from/{from}/to/{to}")
    public ListMessagesDataResponse getMessageList(
            @PathVariable("from") String from, @PathVariable("to") String to) throws CustomIllegalArgumentException {
        ListMessagesDataResponse response = messageService.getMessageListByRange(from, to);
        logger.info("Получено {} сообщений в диапазоне дат {} {}", response.getMessageList().size(), from, to);
        return response;
    }

    @PostMapping("/message")
    public ResponseEntity<ConfirmPostMessage> sendMessage(@RequestBody MessageData messageData) {
        logger.info("Отправлено сообщение: \"{}\"", messageData.getText());
        return new ResponseEntity<>(messageService.postMessage(messageData), HttpStatus.OK);
    }

}
