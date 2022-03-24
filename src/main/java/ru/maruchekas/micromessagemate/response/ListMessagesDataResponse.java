package ru.maruchekas.micromessagemate.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.maruchekas.micromessagemate.data.MessageData;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListMessagesDataResponse {

    private Long total;
    private List<MessageData> messageList;
}
