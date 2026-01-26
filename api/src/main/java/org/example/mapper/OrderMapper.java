package org.example.mapper;

import org.example.dto.OrderRequestDto;
import org.example.dto.OrderResponseDto;
import org.example.model.Order;

public class OrderMapper {
    public static Order toEntity(OrderRequestDto dto) {
        Order order = new Order();
        order.setUsername(dto.getUsername());
        order.setDescription(dto.getDescription());
        order.setAmount(dto.getAmount());
        return order;
    }

    public static OrderResponseDto toDto(Order order) {
        OrderResponseDto dto = new OrderResponseDto();
        dto.setId(order.getId());
        dto.setUsername(order.getUsername());
        dto.setCreatedAt(order.getCreatedAt());
        dto.setStatus(order.getStatus());
        dto.setDescription(order.getDescription());
        dto.setAmount(order.getAmount());
        return dto;
    }
}