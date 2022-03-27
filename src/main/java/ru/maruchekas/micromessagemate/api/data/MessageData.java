package ru.maruchekas.micromessagemate.api.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.Accessors;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class MessageData {
    private Long id;
    private String text;

    @JsonProperty("created_time")
    private String createdTime;

}