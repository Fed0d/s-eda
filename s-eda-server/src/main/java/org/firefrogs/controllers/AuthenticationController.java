package org.firefrogs.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.firefrogs.dto.AuthenticateRequest;
import org.firefrogs.dto.AuthenticateResponse;
import org.firefrogs.dto.RegisterRequest;
import org.firefrogs.services.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
@Tag(name = "Аутентификация")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @Operation(summary = "Регистрация пользователя")
    @PostMapping("/register")
    public ResponseEntity<AuthenticateResponse> register(@RequestBody RegisterRequest registerDTO) {
        return ResponseEntity.ok(authenticationService.register(registerDTO));
    }

    @Operation(summary = "Аутентификация пользователя")
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticateResponse> authenticate(@RequestBody AuthenticateRequest authenticateDTO) {
        return ResponseEntity.ok(authenticationService.authenticate(authenticateDTO));
    }
}
