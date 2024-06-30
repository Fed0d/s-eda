package org.firefrogs.services;

import lombok.AllArgsConstructor;
import org.firefrogs.dto.UserEditRequest;
import org.firefrogs.entities.User;
import org.firefrogs.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    UserRepository userRepository;

    public User findUserById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);

        return optionalUser.orElseThrow();
     }

    public User editUser(Long userId, UserEditRequest userEditRequest) {
        User user = findUserById(userId);
        user.setPassword(userEditRequest.getPassword());

        return userRepository.save(user);
    }
}
