package ru.maruchekas.micromessagemate.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ru.maruchekas.micromessagemate.data.MessageData;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ListMessagesDataResponse {

    private Long total;
    private List<MessageData> messageList;
}
