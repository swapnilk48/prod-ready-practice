package com.practice.prod_features.services;

import com.practice.prod_features.dto.LoginDTO;
import com.practice.prod_features.dto.SignUpDTO;
import com.practice.prod_features.dto.UserDTO;
import com.practice.prod_features.entities.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDetails loadUserByUsername(String username);

    UserEntity getUserById(Long userId);
}
