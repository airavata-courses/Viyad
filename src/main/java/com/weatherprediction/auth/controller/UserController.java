package com.weatherprediction.auth.controller;


import com.weatherprediction.auth.dto.UserDTO;
import com.weatherprediction.auth.model.User;
import com.weatherprediction.auth.security.JwtTokenUtil;
import com.weatherprediction.auth.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private static final String REGISTER_PATH = "/register";
    private static final String LOGIN_PATH = "/login";
    private static final String DEFAULT_PATH = "/";
    public static final String BEARER = "Bearer";

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;


    @GetMapping(DEFAULT_PATH)
    public ResponseEntity<String> defaultPath() {
        return ResponseEntity.ok("Valid Token");
    }

    @PostMapping(REGISTER_PATH)
    public ResponseEntity<UserDTO> registerUser(@Valid @RequestBody UserDTO userDTO) {
        userService.registerUser(userDTO);
        return ResponseEntity.ok(null);
    }

    @PostMapping(LOGIN_PATH)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody UserDTO authenticationRequest) throws Exception {
        final Authentication authResult = authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authResult);
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            User userProfile = userService.findUserByUsername(authenticationRequest.getUsername());
            String jwtToken = jwtTokenUtil.generateToken(authResult);
            ResponseCookie sessionCookie = ResponseCookie.from("accessCookie", BEARER + jwtToken)
                    .httpOnly(true)
                    .secure(true)
                    .maxAge(60 * 60 * 24 * 14) // Set cookie to expire after two weeks
                    .path("/")
                    .sameSite("None")
                    .build();
            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, sessionCookie.toString())
                    .body(userProfile);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<User> getUserDetails(@PathVariable Long userId) {
        Optional<User> user = userService.findById(userId);
        if (!user.isPresent()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(user.get(), HttpStatus.OK);
    }

    private Authentication authenticate(String username, String password) throws Exception {
        try {
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

}
