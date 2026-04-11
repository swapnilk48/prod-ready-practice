package com.practice.prod_features.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class SignUpDTO {
    private String email;

    private String password;

    private String name;
}
