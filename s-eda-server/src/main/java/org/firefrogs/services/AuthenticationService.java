package org.firefrogs.services;

import lombok.AllArgsConstructor;
import org.firefrogs.dto.AuthenticateRequest;
import org.firefrogs.dto.AuthenticateResponse;
import org.firefrogs.dto.RegisterRequest;
import org.firefrogs.entities.User;
import org.firefrogs.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static org.firefrogs.entities.Role.ROLE_USER;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticateResponse register(RegisterRequest registerRequest) {
        User user = User.builder()
                .nickname(registerRequest.getNickname())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(ROLE_USER)
                .build();

        userRepository.save(user);

        String jwt = jwtService.generateToken(user);
        return AuthenticateResponse.builder()
                .jwt(jwt)
                .build();
    }

    public AuthenticateResponse authenticate(AuthenticateRequest authenticateRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        authenticateRequest.getNickname(),
                        authenticateRequest.getPassword()
                )
        );

        User user = userRepository.findByNickname(authenticateRequest.getNickname()).orElseThrow();

        String jwt = jwtService.generateToken(user);
        return AuthenticateResponse.builder()
                .jwt(jwt)
                .build();
    }
}
