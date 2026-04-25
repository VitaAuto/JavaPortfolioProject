package org.example.service;

import org.example.dto.OrderRequestDto;
import org.example.dto.OrderResponseDto;
import org.example.exception.OrderNotFoundException;
import org.example.mapper.OrderMapper;
import org.example.model.Order;
import org.example.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<OrderResponseDto> getAll() {
        return orderRepository.findAll().stream()
                .map(OrderMapper::toDto)
                .collect(Collectors.toList());
    }

    public OrderResponseDto getById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        return OrderMapper.toDto(order);
    }

    public OrderResponseDto create(OrderRequestDto dto) {
        Order order = OrderMapper.toEntity(dto);
        order.setId(null);
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus("CREATED");
        Order saved = orderRepository.save(order);
        return OrderMapper.toDto(saved);
    }

    public OrderResponseDto update(Long id, OrderRequestDto dto) {
        Order existing = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        Order order = OrderMapper.toEntity(dto);
        order.setId(id);
        order.setCreatedAt(existing.getCreatedAt());
        order.setStatus("UPDATED");
        Order saved = orderRepository.save(order);
        return OrderMapper.toDto(saved);
    }

    public OrderResponseDto patch(Long id, Map<String, Object> updates) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        updates.forEach((key, value) -> {
            switch (key) {
                case "username" -> order.setUsername((String) value);
                case "status" -> order.setStatus((String) value);
                case "description" -> order.setDescription((String) value);
                case "amount" -> order.setAmount(Double.valueOf(value.toString()));
                default -> throw new IllegalArgumentException("Unknown field: " + key);
            }
        });
        order.setStatus("PARTIALLY_UPDATED");
        Order saved = orderRepository.save(order);
        return OrderMapper.toDto(saved);
    }

    public OrderResponseDto delete(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        order.setStatus("DELETED");
        Order saved = orderRepository.save(order);
        return OrderMapper.toDto(saved);
    }

    public void hardDelete(Long id) {
        if (!orderRepository.existsById(id)) throw new OrderNotFoundException(id);
        orderRepository.deleteById(id);
    }
}