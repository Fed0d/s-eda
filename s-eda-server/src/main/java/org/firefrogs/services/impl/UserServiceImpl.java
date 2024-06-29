package org.firefrogs.services.impl;

import lombok.AllArgsConstructor;
import org.firefrogs.DTO.UserEditDTO;
import org.firefrogs.entities.User;
import org.firefrogs.repositories.UserRepository;
import org.firefrogs.services.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    UserRepository userRepository;

    @Override
    public User findUserById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);

        return optionalUser.orElseThrow();
     }

    @Override
    public User editUser(Long userId, UserEditDTO userEditDTO) {
        User user = findUserById(userId);
        user.setPassword(userEditDTO.getPassword());

        return userRepository.save(user);
    }
}
