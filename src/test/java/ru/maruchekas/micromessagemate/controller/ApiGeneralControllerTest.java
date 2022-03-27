package ru.maruchekas.micromessagemate.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.maruchekas.micromessagemate.AbstractTest;
import ru.maruchekas.micromessagemate.api.data.MessageData;
import ru.maruchekas.micromessagemate.service.ApiGeneralService;

import java.time.LocalDateTime;

import static ru.maruchekas.micromessagemate.appconfig.Constants.INVALID_ARGUMENT_ERR;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(value = {"classpath:application-test.properties"})
public class ApiGeneralControllerTest extends AbstractTest {

    @Autowired
    ApiGeneralService apiGeneralService;

    @BeforeEach
    public void setup() {
        super.setup();
    }

    @AfterEach
    public void cleanup() {
    }


    @Test
    void sendMessageTest() throws Exception {
        MessageData messageData = new MessageData();
        messageData.setText("test text");
        String ctime = LocalDateTime.now().toString().substring(0,17);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/message")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(messageData)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.text").value(messageData.getText()))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(ctime)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getMessageListTest() throws Exception {

        String fromTime = LocalDateTime.now().minusDays(1L).toString();
        String toTime = LocalDateTime.now().toString();

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/message/from/{from}/to/{to}", fromTime, toTime)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.total").exists())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getMessageListBadTest() throws Exception {

        String fromTime = LocalDateTime.now().minusDays(1L).toString();
        String toTime = LocalDateTime.now().toString();

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/message/from/"+ fromTime.replace('T', ' ') + "/to/" + toTime)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.total").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(INVALID_ARGUMENT_ERR))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
