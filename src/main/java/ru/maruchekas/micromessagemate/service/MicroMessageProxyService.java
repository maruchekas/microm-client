package ru.maruchekas.micromessagemate.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import ru.maruchekas.micromessagemate.api.data.AuthData;
import ru.maruchekas.micromessagemate.api.data.MessageData;
import ru.maruchekas.micromessagemate.api.data.UserData;
import ru.maruchekas.micromessagemate.api.response.ConfirmLoginResponse;
import ru.maruchekas.micromessagemate.api.response.ListMessagesDataResponse;

import java.util.List;

@Service
@FeignClient(name = "message-server", url = "localhost:8080")
public interface MicroMessageProxyService {

    @PostMapping("/api/auth/login")
    ConfirmLoginResponse login(@RequestBody AuthData authData);

    @GetMapping("/api/message/{id}")
    MessageData returnMessageData(@RequestHeader("Authorization") String token, @PathVariable("id") Long id);

    @GetMapping("/api/message/from/{from}/to/{to}")
    ListMessagesDataResponse returnMessageList(@RequestHeader("Authorization") String token,
                                               @PathVariable("from") String from, @PathVariable("to") String to);

    @PostMapping("/api/message")
    MessageData postMessageData(@RequestHeader("Authorization") String token, @RequestBody MessageData messageData);

    @GetMapping("/api/auth/users")
    List<UserData> getAllUsers(@RequestHeader("Authorization") String token);

}
