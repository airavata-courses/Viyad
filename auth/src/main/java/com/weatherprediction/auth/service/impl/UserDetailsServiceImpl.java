package com.weatherprediction.auth.service.impl;


import com.weatherprediction.auth.dto.UserDTO;
import com.weatherprediction.auth.dto.mapper.UserMapper;
import com.weatherprediction.auth.enums.Role;
import com.weatherprediction.auth.model.User;
import com.weatherprediction.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional

public class UserDetailsServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDTO registerUser(UserDTO userDTO) {
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        Role role = userDTO.getRole();
        User user = UserMapper.toUser(userDTO);
        user.setRole(role);
        userRepository.save(user);
        return UserMapper.toUserDto(user);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(loadUserByUsername(username).getUsername());
    }

    @Override
    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        SimpleGrantedAuthority userRole = new SimpleGrantedAuthority(user.getRole().getValue());
        authorities.add(userRole);

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPasswordHash(),
                authorities);
    }
}
