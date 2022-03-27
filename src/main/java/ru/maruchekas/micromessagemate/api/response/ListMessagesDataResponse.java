package ru.maruchekas.micromessagemate.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ru.maruchekas.micromessagemate.api.data.MessageData;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ListMessagesDataResponse {

    private Long total;
    private List<MessageData> messageList;
}
