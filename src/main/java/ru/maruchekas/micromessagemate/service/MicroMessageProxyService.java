package ru.maruchekas.micromessagemate.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import ru.maruchekas.micromessagemate.data.ListMessagesData;
import ru.maruchekas.micromessagemate.data.MessageData;

@Service
@FeignClient(name = "message-server", url = "localhost:8080")
public interface MicroMessageProxyService {

    @GetMapping("/api/message/{id}")
    MessageData returnMessageData(@PathVariable("id") Long id);

    @GetMapping("/api/message/from/{from}/to/{to}")
    ListMessagesData returnMessageList(@PathVariable("from") String from, @PathVariable("to") String to);

    @PostMapping("/api/message")
    MessageData putMessageData(@RequestBody MessageData messageData);
}
