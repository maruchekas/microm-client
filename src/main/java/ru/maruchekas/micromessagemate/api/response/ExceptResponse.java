package ru.maruchekas.micromessagemate.api.response;

import lombok.*;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ExceptResponse {

    private String error;
    private String message;
}
