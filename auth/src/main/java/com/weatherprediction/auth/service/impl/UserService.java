package com.weatherprediction.auth.service.impl;

import com.weatherprediction.auth.dto.UserDTO;
import com.weatherprediction.auth.model.User;

import java.util.Optional;

public interface UserService {
    UserDTO registerUser(UserDTO userDTO);

    User findUserByUsername(String username);

    Optional<User> findById(Long userId);
}
