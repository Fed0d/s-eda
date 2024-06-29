package org.firefrogs.controllers;

import lombok.AllArgsConstructor;
import org.firefrogs.DTO.UserEditDTO;
import org.firefrogs.entities.User;
import org.firefrogs.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(userService.findUserById(userId));
    }

    @PatchMapping("/edit/{userId}")
    public ResponseEntity<User> editUser(@PathVariable("userId") Long userId, @RequestBody UserEditDTO userEditDTO) {
        return ResponseEntity.ok(userService.editUser(userId, userEditDTO));
    }
}
