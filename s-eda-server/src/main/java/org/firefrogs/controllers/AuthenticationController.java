package org.firefrogs.controllers;

import lombok.AllArgsConstructor;
import org.firefrogs.DTO.AuthenticateDTO;
import org.firefrogs.DTO.AuthenticationDTO;
import org.firefrogs.DTO.RegisterDTO;
import org.firefrogs.services.impl.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationDTO> register(@RequestBody RegisterDTO registerDTO) {
        return ResponseEntity.ok(authenticationService.register(registerDTO));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationDTO> authenticate(@RequestBody AuthenticateDTO authenticateDTO) {
        return ResponseEntity.ok(authenticationService.authenticate(authenticateDTO));
    }
}
