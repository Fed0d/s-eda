package org.firefrogs.services;

import org.firefrogs.DTO.UserEditDTO;
import org.firefrogs.entities.User;

public interface UserService {
    User findUserById(Long userId);
    User editUser(Long userId, UserEditDTO userEditDTO);
}
