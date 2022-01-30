package com.weatherprediction.auth.dto.mapper;


import com.weatherprediction.auth.dto.UserDTO;
import com.weatherprediction.auth.model.User;

public class UserMapper {
    public static User toUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPasswordHash(userDTO.getPassword());
        return user;
    }

    public static UserDTO toUserDto(User user) {
        return new UserDTO()
                .setUsername(user.getUsername())
                .setRole(user.getRole());
    }
}
