package ru.maruchekas.micromessagemate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private MicroMessageProxy proxy;

    @Autowired
    private Environment environment;

    @GetMapping("/api/message/{id}")
    public ReturnedMessageData convertCurrencyFeign(@PathVariable("id") Long id) {
        ReturnedMessageData response = proxy.returnMessageData(id);
        logger.info("{}", response);
        System.out.println(response);
        return new ReturnedMessageData(response.getId(), response.getText(), response.getCreatedTime());
    }

}
