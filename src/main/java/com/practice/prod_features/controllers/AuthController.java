package com.practice.prod_features.controllers;

import com.practice.prod_features.dto.LoginDTO;
import com.practice.prod_features.dto.LoginResponseDTO;
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
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

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
    public ResponseEntity<LoginResponseDTO> loginUser(@RequestBody LoginDTO loginDTO, HttpServletRequest httpServeletRequest,
                                            HttpServletResponse httpServletResponse){
        LoginResponseDTO token = authService.login(loginDTO);

        Cookie cookie1 = new Cookie("accessToken", token.getAccessToken());

        Cookie cookie2 = new Cookie("refreshToken", token.getRefreshToken());

        cookie1.setHttpOnly(true);

        cookie2.setHttpOnly(true);

        httpServletResponse.addCookie(cookie1);

        httpServletResponse.addCookie(cookie2);

        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDTO> refresh(HttpServletRequest request){
        String refreshToken = Arrays.stream(request.getCookies()).
                filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new AuthenticationServiceException("Refresh token not found inside the cookies"));

        LoginResponseDTO loginResponseDTO = authService.refreshToken(refreshToken);

        return new ResponseEntity<>(loginResponseDTO, HttpStatus.OK);
    }
}
