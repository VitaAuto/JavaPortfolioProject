package org.example.services;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.example.context.ApiScenarioContext;
import org.example.models.LoginRequestDto;
import org.example.models.OrderRequestDto;
import org.example.models.OrderResponseDto;
import io.restassured.response.Response;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ApiOrderService {

    public String resolveUsername(String usernameType) {
        return switch (usernameType.toLowerCase()) {
            case "valid" -> System.getenv("VALID_API_LOGIN");
            case "invalid" -> System.getenv("INVALID_API_LOGIN");
            default -> throw new IllegalArgumentException("Unknown username type: " + usernameType);
        };
    }

    public String resolvePassword(String passwordType) {
        return switch (passwordType.toLowerCase()) {
            case "valid" -> System.getenv("VALID_API_PASSWORD");
            case "invalid" -> System.getenv("INVALID_API_PASSWORD");
            default -> throw new IllegalArgumentException("Unknown password type: " + passwordType);
        };
    }

    public Long resolveId(String idStr, ApiScenarioContext context) {
        if ("saved".equalsIgnoreCase(idStr) || "<saved>".equals(idStr)) {
            return context.get("saved_id", Long.class);
        }
        return Long.valueOf(idStr);
    }

    public OrderRequestDto buildOrderRequest(String username, String description, String amountStr) {
        OrderRequestDto dto = new OrderRequestDto();
        dto.setUsername(username);
        dto.setDescription(description);
        if (amountStr != null && !amountStr.isBlank()) {
            try {
                dto.setAmount(Double.valueOf(amountStr));
            } catch (NumberFormatException e) {
                dto.setAmount(null);
            }
        } else {
            dto.setAmount(null);
        }
        return dto;
    }

    public LoginRequestDto buildLoginRequest(String username, String password) {
        LoginRequestDto dto = new LoginRequestDto();
        dto.setUsername(username);
        dto.setPassword(password);
        return dto;
    }

    public void assertOrdersPresence(Response response, String not) {
        var orders = response.jsonPath().getList("$", OrderResponseDto.class);
        log.info("Orders found: {}", orders);
        if (not != null && not.trim().equals("not")) {
            Assertions.assertThat(orders).isEmpty();
        } else {
            Assertions.assertThat(orders).isNotEmpty();
        }
    }

    public void saveOrderIdIfCreated(Response response, ApiScenarioContext context) {
        if (response.getStatusCode() == 200 || response.getStatusCode() == 201) {
            var createdOrder = response.as(OrderResponseDto.class);
            context.set("saved_id", createdOrder.getId());
            log.info("Order created with id: {}", createdOrder.getId());
        }
    }
}