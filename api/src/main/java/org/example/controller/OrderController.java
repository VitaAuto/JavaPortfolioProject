package org.example.controller;

import jakarta.validation.Valid;
import org.example.dto.OrderRequestDto;
import org.example.dto.OrderResponseDto;
import org.example.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<OrderResponseDto> getAll() {
        return orderService.getAll();
    }

    @GetMapping("/{id}")
    public OrderResponseDto getById(@PathVariable Long id) {
        return orderService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponseDto create(@Valid @RequestBody OrderRequestDto dto) {
        return orderService.create(dto);
    }

    @PutMapping("/{id}")
    public OrderResponseDto update(@PathVariable Long id, @Valid @RequestBody OrderRequestDto dto) {
        return orderService.update(id, dto);
    }

    @PatchMapping("/{id}")
    public OrderResponseDto patch(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        return orderService.patch(id, updates);
    }

    @DeleteMapping("/{id}")
    public OrderResponseDto delete(@PathVariable Long id) {
        return orderService.delete(id);
    }

    @DeleteMapping("/hard/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void hardDelete(@PathVariable Long id) {
        orderService.hardDelete(id);
    }
}