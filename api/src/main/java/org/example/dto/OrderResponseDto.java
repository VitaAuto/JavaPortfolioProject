package org.example.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderResponseDto {
    private Long id;
    private String username;
    private LocalDateTime createdAt;
    private String status;
    private String description;
    private Double amount;
}