package org.example.models;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String username;
    private String password;
}