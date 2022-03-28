package ru.maruchekas.micromessagemate.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.maruchekas.micromessagemate.api.data.AuthData;
import ru.maruchekas.micromessagemate.api.data.UserData;
import ru.maruchekas.micromessagemate.exception.AccessDeniedException;
import ru.maruchekas.micromessagemate.exception.IncorrectLoginPasswordPairException;
import ru.maruchekas.micromessagemate.api.response.ConfirmLoginResponse;
import ru.maruchekas.micromessagemate.api.response.ListMessagesDataResponse;
import ru.maruchekas.micromessagemate.api.data.MessageData;
import ru.maruchekas.micromessagemate.exception.CustomIllegalArgumentException;
import ru.maruchekas.micromessagemate.api.response.ConfirmPostMessage;
import ru.maruchekas.micromessagemate.exception.MessageNotFoundException;
import ru.maruchekas.micromessagemate.service.ApiGeneralService;

import java.util.List;

@RestController
@Tag(name = "Контроллер клиента для работы с сообщениями")
@RequestMapping("/api")
public class ApiGeneralController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ApiGeneralService apiGeneralService;

    @Operation(summary = "Авторизация на сервере")
    @PostMapping("/auth/login")
    public ResponseEntity<ConfirmLoginResponse> login(@RequestBody AuthData authData)
            throws IncorrectLoginPasswordPairException {
        logger.info("Попытка входа пользователя \"{}\"", authData.getEmail());
        return new ResponseEntity<>(apiGeneralService.loginUser(authData), HttpStatus.OK);
    }

    @Operation(summary = "Запрос списка всех пользователей с сервера")
    @GetMapping("/auth/users")
    public ResponseEntity<List<UserData>> getAllUsers(@RequestHeader("Authorization") String token)
            throws AccessDeniedException {
        logger.info("Получение списка всех пользователей");
        return new ResponseEntity<>(apiGeneralService.getAllUsers(token), HttpStatus.OK);
    }

    @Operation(summary = "Получение сообщения с сервера по id")
    @GetMapping("/message/{id}")
    public MessageData getMessage(@RequestHeader("Authorization") String token, @PathVariable("id") Long id)
            throws MessageNotFoundException {
        logger.info("Получено сообщение номер {}", id);
        return apiGeneralService.getMessage(token, id);
    }

    @Operation(summary = "Получение списка сообщений с сервера в диапазоне дат")
    @GetMapping("/message/from/{from}/to/{to}")
    public ListMessagesDataResponse getMessageList(@RequestHeader("Authorization") String token,
            @PathVariable("from") String from, @PathVariable("to") String to) throws CustomIllegalArgumentException {
        ListMessagesDataResponse response = apiGeneralService.getMessageListByRange(token, from, to);
        logger.info("Получено {} сообщений в диапазоне дат {} {}", response.getMessageList().size(), from, to);
        return response;
    }

    @Operation(summary = "Отправка сообщения на сервер")
    @PostMapping("/message")
    public ResponseEntity<ConfirmPostMessage> sendMessage(@RequestHeader("Authorization") String token,
                                                          @RequestBody MessageData messageData) throws AccessDeniedException {
        logger.info("Отправлено сообщение: \"{}\"", messageData.getText());
        return new ResponseEntity<>(apiGeneralService.postMessage(token, messageData), HttpStatus.OK);
    }


}
