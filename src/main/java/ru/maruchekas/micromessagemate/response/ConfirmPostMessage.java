package ru.maruchekas.micromessagemate.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ConfirmPostMessage {

    private Long id;
    private String text;

    @JsonProperty("created_time")
    private String createdTime;
}
