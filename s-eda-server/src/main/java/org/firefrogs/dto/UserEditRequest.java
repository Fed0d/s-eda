package org.firefrogs.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Запрос на изменение данных пользователя")
public class UserEditRequest {
    @Schema(description = "Пароль", example = "password")
    @Size(min = 3, max = 20, message = "Пароль должен быть от 3 до 20 символов")
    @NotBlank(message = "Пароль не должен быть пустым")
    private String password;
}
