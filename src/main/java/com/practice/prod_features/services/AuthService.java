package com.practice.prod_features.services;

import com.practice.prod_features.dto.LoginDTO;
import com.practice.prod_features.dto.LoginResponseDTO;
import com.practice.prod_features.dto.SignUpDTO;
import com.practice.prod_features.dto.UserDTO;
import com.practice.prod_features.entities.SessionEntity;
import com.practice.prod_features.entities.UserEntity;
import com.practice.prod_features.exceptions.UserAlreadyExists;
import com.practice.prod_features.repositories.SessionRepository;
import com.practice.prod_features.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final SessionRepository sessionRepository;

    private final UserService userService;

    public UserDTO signUp(SignUpDTO signUpDTO) {
        String email = signUpDTO.getEmail();

        Optional<UserEntity> user = userRepository.findByEmail(email);

        if(user.isPresent()){
            throw new UserAlreadyExists("User already exists with email ID:"+email);
        }
        UserEntity toCreate = modelMapper.map(signUpDTO, UserEntity.class);
        toCreate.setPassword(passwordEncoder.encode(toCreate.getPassword()));
        UserEntity savedUser = userRepository.save(toCreate);

        return modelMapper.map(savedUser, UserDTO.class);
    }

    public LoginResponseDTO login(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
        );

        UserEntity user = (UserEntity) authentication.getPrincipal();

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        saveSession(user, accessToken, refreshToken);

        return new LoginResponseDTO(user.getId(), accessToken, refreshToken);
    }

    public LoginResponseDTO refreshToken(String refreshToken) {
        Long userId = jwtService.getUserIdFromToken(refreshToken);
        if (!sessionRepository.existsByUser_IdAndRefreshToken(userId, refreshToken)) {
            throw new AuthenticationServiceException("Refresh token expired because you logged in from another device");
        }

        UserEntity userEntity = userService.getUserById(userId);

        String accessToken = jwtService.generateAccessToken(userEntity);
        saveSession(userEntity, accessToken, refreshToken);

        return new LoginResponseDTO(userEntity.getId(), accessToken, refreshToken);
    }

    private void saveSession(UserEntity user, String accessToken, String refreshToken) {
        SessionEntity session = sessionRepository.findByUser_Id(user.getId())
                .orElseGet(SessionEntity::new);

        session.setUser(user);
        session.setToken(accessToken);
        session.setRefreshToken(refreshToken);
        session.setCreatedAt(LocalDateTime.now());
        sessionRepository.save(session);
    }
}
