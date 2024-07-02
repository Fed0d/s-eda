package org.firefrogs.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.firefrogs.dto.UserEditRequest;
import org.firefrogs.entities.User;
import org.firefrogs.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
@Tag(name = "Пользователи")
public class UserController {
    private final UserService userService;

    @Operation(summary = "Получение пользователя по id")
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(userService.findUserById(userId));
    }

    @Operation(summary = "Изменение данных пользователя")
    @PatchMapping("/edit/{userId}")
    public ResponseEntity<User> editUser(@PathVariable("userId") Long userId, @Valid @RequestBody UserEditRequest userEditRequest) {
        return ResponseEntity.ok(userService.editUser(userId, userEditRequest));
    }
}
