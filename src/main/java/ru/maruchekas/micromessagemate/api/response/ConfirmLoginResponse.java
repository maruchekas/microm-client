package ru.maruchekas.micromessagemate.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ConfirmLoginResponse {

    private Long userId;
    private String token;
}
