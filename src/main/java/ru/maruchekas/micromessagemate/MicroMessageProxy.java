package ru.maruchekas.micromessagemate;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "message-server", url = "localhost:8080")
public interface MicroMessageProxy {

    @GetMapping("/api/message/{id}")
    ReturnedMessageData returnMessageData(@PathVariable("id") Long id);
}
