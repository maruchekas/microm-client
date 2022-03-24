package ru.maruchekas.micromessagemate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.maruchekas.micromessagemate.data.ListMessagesData;
import ru.maruchekas.micromessagemate.data.MessageData;
import ru.maruchekas.micromessagemate.service.MicroMessageProxyService;

@RestController
@RequestMapping("/api")
public class MessageController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private MicroMessageProxyService proxy;

    @GetMapping("/message/{id}")
    public MessageData getMessage(@PathVariable("id") Long id) {
        MessageData response = proxy.returnMessageData(id);
        logger.info("{}", response);
        return new MessageData(response.getId(), response.getText(), response.getCreatedTime());
    }

    @GetMapping("/message/from/{from}/to/{to}")
    public ListMessagesData getMessageList(@PathVariable("from") String from, @PathVariable("to") String to) {
        ListMessagesData response = proxy.returnMessageList(from, to);
        logger.info("Получено {} сообщений в диапазоне дат {} {}", response.getMessageList().size(), from, to);
        return new ListMessagesData(response.getTotal(), response.getMessageList());
    }

    @PostMapping("/message")
    public MessageData sendMessage(@RequestBody MessageData messageData) {
        MessageData response = proxy.putMessageData(messageData);
        logger.info("Отправлено сообщение {}", response);
        return new MessageData(response.getId(), response.getText(), response.getCreatedTime());
    }

}
