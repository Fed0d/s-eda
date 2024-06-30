package org.firefrogs.services.impl;

import lombok.AllArgsConstructor;
import org.firefrogs.DTO.AuthenticateDTO;
import org.firefrogs.DTO.AuthenticationDTO;
import org.firefrogs.DTO.RegisterDTO;
import org.firefrogs.entities.Role;
import org.firefrogs.entities.User;
import org.firefrogs.repositories.RoleRepository;
import org.firefrogs.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;

    public AuthenticationDTO register(RegisterDTO registerDTO) {
        User user = User.builder()
                .nickname(registerDTO.getNickname())
                .password(passwordEncoder.encode(registerDTO.getPassword()))
                .role(roleRepository.findByName(Role.RoleName.USER).orElseThrow())
                .build();

        userRepository.save(user);

        String jwt = jwtService.generateToken(user);
        return AuthenticationDTO.builder()
                .jwt(jwt)
                .build();
    }

    public AuthenticationDTO authenticate(AuthenticateDTO authenticateDTO) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        authenticateDTO.getNickname(),
                        authenticateDTO.getPassword()
                )
        );

        User user = userRepository.findByNickname(authenticateDTO.getNickname()).orElseThrow();

        String jwt = jwtService.generateToken(user);
        return AuthenticationDTO.builder()
                .jwt(jwt)
                .build();
    }
}
