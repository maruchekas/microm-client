package ru.maruchekas.micromessagemate.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.maruchekas.micromessagemate.data.AuthData;
import ru.maruchekas.micromessagemate.data.UserData;
import ru.maruchekas.micromessagemate.response.ConfirmLoginResponse;
import ru.maruchekas.micromessagemate.response.ListMessagesDataResponse;
import ru.maruchekas.micromessagemate.data.MessageData;
import ru.maruchekas.micromessagemate.exception.CustomIllegalArgumentException;
import ru.maruchekas.micromessagemate.response.ConfirmPostMessage;
import ru.maruchekas.micromessagemate.service.MessageService;
import ru.maruchekas.micromessagemate.service.MicroMessageProxyService;

import java.util.List;

@RestController
@Tag(name = "Контроллер клиента для работы с сообщениями")
@RequestMapping("/api")
public class MessageController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private MicroMessageProxyService proxy;

    @Autowired
    MessageService messageService;

    @Operation(summary = "Авторизация на сервере")
    @PostMapping("/auth/login")
    public ResponseEntity<ConfirmLoginResponse> login(@RequestBody AuthData authData) {
        logger.info("Попытка входа пользователя \"{}\"", authData.getEmail());
        return new ResponseEntity<>(messageService.loginUser(authData), HttpStatus.OK);
    }

    @Operation(summary = "Запрос списка всех пользователей с сервера")
    @GetMapping("/auth/users")
    public ResponseEntity<List<UserData>> getAllUsers() {
        logger.info("Получение списка всех пользователей");
        return new ResponseEntity<>(messageService.getAllUsers(), HttpStatus.OK);
    }

    @Operation(summary = "Получение сообщения с сервера по id")
    @GetMapping("/message/{id}")
    public MessageData getMessage(@PathVariable("id") Long id) {
        logger.info("Получено сообщение номер {}", id);
        return messageService.getMessage(id);
    }

    @Operation(summary = "Получение списка сообщений с сервера в диапазоне дат")
    @GetMapping("/message/from/{from}/to/{to}")
    public ListMessagesDataResponse getMessageList(
            @PathVariable("from") String from, @PathVariable("to") String to) throws CustomIllegalArgumentException {
        ListMessagesDataResponse response = messageService.getMessageListByRange(from, to);
        logger.info("Получено {} сообщений в диапазоне дат {} {}", response.getMessageList().size(), from, to);
        return response;
    }

    @Operation(summary = "Отправка сообщения на сервер")
    @PostMapping("/message")
    public ResponseEntity<ConfirmPostMessage> sendMessage(@RequestBody MessageData messageData) {
        logger.info("Отправлено сообщение: \"{}\"", messageData.getText());
        return new ResponseEntity<>(messageService.postMessage(messageData), HttpStatus.OK);
    }


}
