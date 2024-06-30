package org.firefrogs.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Запрос на регистрацию")
public class RegisterRequest {
    @Schema(description = "Никнейм", example = "user")
    private String nickname;
    @Schema(description = "Пароль", example = "password")
    private String password;
}
