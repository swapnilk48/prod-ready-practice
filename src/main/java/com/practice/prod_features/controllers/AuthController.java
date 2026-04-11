package com.practice.prod_features.controllers;

import com.practice.prod_features.dto.LoginDTO;
import com.practice.prod_features.dto.SignUpDTO;
import com.practice.prod_features.dto.UserDTO;
import com.practice.prod_features.services.AuthService;
import com.practice.prod_features.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> createUser(@RequestBody SignUpDTO signUpDTO){
        UserDTO userDTO = authService.signUp(signUpDTO);
        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginDTO loginDTO, HttpServletRequest httpServeletRequest,
                                            HttpServletResponse httpServletResponse){
        String token = authService.login(loginDTO);

        Cookie cookie = new Cookie("token", token);

        cookie.setHttpOnly(true);

        httpServletResponse.addCookie(cookie);

        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}
