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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.maruchekas.micromessagemate.AbstractTest;
import ru.maruchekas.micromessagemate.api.data.AuthData;
import ru.maruchekas.micromessagemate.api.data.MessageData;
import ru.maruchekas.micromessagemate.appconfig.security.JwtGenerator;
import ru.maruchekas.micromessagemate.service.ApiGeneralService;

import java.time.LocalDateTime;

import static ru.maruchekas.micromessagemate.appconfig.Constants.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(value = {"classpath:application-test.properties"})
public class ApiGeneralControllerTest extends AbstractTest {

    @Autowired
    ApiGeneralService apiGeneralService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    JwtGenerator jwtGenerator;

    private static String jwtToken;

    private String userEmail;
    private String adminEmail;
    private String userPass;
    private String adminPass;

    @BeforeEach
    public void setup() {
        super.setup();
        userEmail = "user@mail.ru";
        adminEmail = "admin@mail.ru";
        userPass = "user";
        adminPass = "admin";
    }

    @AfterEach
    public void cleanup() {
    }

    @Test
    void loginUserTest() throws Exception {

        AuthData authData = new AuthData();
        authData.setEmail(adminEmail);
        authData.setPassword(adminPass);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(authData)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").exists());
    }

    @Test
    void loginUserBadTest() throws Exception {

        AuthData authData = new AuthData();
        authData.setEmail(adminEmail);
        authData.setPassword(userPass);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(authData)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(INCORRECT_LOGIN_OR_PASSWORD));
    }

    @Test
    void sendMessageTest() throws Exception {

        jwtToken = jwtGenerator.generateToken(adminEmail);
        MessageData messageData = new MessageData();
        messageData.setText("test text");
        String ctime = LocalDateTime.now().toString().substring(0,17);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/message")
                        .contentType(MediaType.APPLICATION_JSON).header("Authorization", jwtToken)
                        .content(mapper.writeValueAsString(messageData)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.text").value(messageData.getText()))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(ctime)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void sendMessageBadTest() throws Exception {
        MessageData messageData = new MessageData();
        messageData.setText("test text");
        jwtToken = jwtGenerator.generateToken(userEmail);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/message")
                        .contentType(MediaType.APPLICATION_JSON).header("Authorization", jwtToken)
                        .content(mapper.writeValueAsString(messageData)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("access"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    void getMessageListTest() throws Exception {

        jwtToken = jwtGenerator.generateToken(userEmail);
        String fromTime = LocalDateTime.now().minusDays(1L).toString();
        String toTime = LocalDateTime.now().toString();

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/message/from/{from}/to/{to}", fromTime, toTime)
                        .contentType(MediaType.APPLICATION_JSON).header("Authorization", jwtToken))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getMessageListBadTest() throws Exception {

        jwtToken = jwtGenerator.generateToken(userEmail);
        String fromTime = LocalDateTime.now().minusDays(1L).toString();
        String toTime = LocalDateTime.now().toString();

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/message/from/"+ fromTime.replace('T', ' ') + "/to/" + toTime)
                        .contentType(MediaType.APPLICATION_JSON).header("Authorization", jwtToken))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(INVALID_ARGUMENT_ERR))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void getMessageByIdTest() throws Exception {
        jwtToken = jwtGenerator.generateToken(userEmail);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/message/{id}", 3)
                .contentType(MediaType.APPLICATION_JSON).header("Authorization", jwtToken))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(3))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getMessageByIdBadTest() throws Exception {
        jwtToken = jwtGenerator.generateToken(userEmail);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/message/{id}", 1000)
                        .contentType(MediaType.APPLICATION_JSON).header("Authorization", jwtToken))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(MESSAGE_NOT_FOUND))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void getMessageByIdBadNotAuthorizedTest() throws Exception {
        jwtToken = jwtGenerator.generateToken(userEmail);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/message/{id}", 1000)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
