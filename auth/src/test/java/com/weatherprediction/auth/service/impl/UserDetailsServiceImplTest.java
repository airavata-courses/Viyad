package com.weatherprediction.auth.service.impl;

import com.weatherprediction.auth.dto.UserDTO;
import com.weatherprediction.auth.enums.Role;
import com.weatherprediction.auth.model.User;
import com.weatherprediction.auth.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserDetailsServiceImpl service;

    private UserDTO userDTO;
    private User user;

    @BeforeEach
    void setUp() {
        userDTO = new UserDTO();
        userDTO.setUsername("testUser");
        userDTO.setPassword("password");
        userDTO.setRole(Role.USER);

        user = new User();
        user.setUsername("testUser");
        user.setPasswordHash("password");
        user.setRole(Role.USER);
    }

    @Test
    void registerUser() {
        when(passwordEncoder.encode(any())).thenReturn("password");
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO returnedUserDTO = service.registerUser(userDTO);

        assertEquals(returnedUserDTO.getUsername(), userDTO.getUsername());
        assertEquals(returnedUserDTO.getRole(), userDTO.getRole());
        assertNull(returnedUserDTO.getPassword());
    }

    @Test
    void loadUserByUsername_returnsUser() {
        when(userRepository.findUserByUsername(user.getUsername())).thenReturn(user);

        UserDetails returnedUser = service.loadUserByUsername(user.getUsername());

        assertEquals(returnedUser.getUsername(), userDTO.getUsername());
    }

    @Test
    void loadUserByUsername_throwsException() {
        when(userRepository.findUserByUsername(user.getUsername())).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername(user.getUsername()));
    }

    @Test
    void findById_returnsUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> returnedUser = service.findById(1L);
        assertEquals(returnedUser.get().getUsername(), user.getUsername());
    }

    @Test
    void findById_returnsNull() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<User> returnedUser = service.findById(1L);

        assertTrue(returnedUser.isEmpty());
    }
}