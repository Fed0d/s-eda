package org.firefrogs.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Запрос на регистрацию")
public class RegisterRequest {
    @Schema(description = "Никнейм", example = "user")
    @Size(min = 3, max = 20, message = "Имя пользователя должно содержать от 3 до 20 символов")
    @NotBlank(message = "Имя пользователя не может быть пустым")
    private String nickname;
    @Schema(description = "Пароль", example = "password")
    @Size(min = 3, max = 20, message = "Длина пароля должна быть от 3 до 20 символов")
    @NotBlank(message = "Пароль не может быть пустым")
    private String password;
}
