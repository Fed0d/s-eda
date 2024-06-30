package org.firefrogs.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Запрос на изменение данных пользователя")
public class UserEditRequest {
    @Schema(description = "Пароль", example = "password")
    private String password;
}
