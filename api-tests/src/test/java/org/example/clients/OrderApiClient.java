package org.example.clients;

import io.restassured.response.Response;
import lombok.Setter;
import org.example.models.OrderRequestDto;
import org.springframework.stereotype.Component;

import static io.restassured.RestAssured.given;
import static org.example.constants.ApiConstants.*;

@Setter
@Component
public class OrderApiClient {
    private String jwtToken;

    public Response getAllOrders() {
        return given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + jwtToken)
                .get(ORDERS);
    }

    public Response createOrder(OrderRequestDto dto, String correlationId) {
        return given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + jwtToken)
                .header("X-Correlation-Id", correlationId)
                .body(dto)
                .post(ORDER);
    }

    public Response getOrder(Long id) {
        return given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + jwtToken)
                .get(ORDER + "/" + id);
    }

    public Response updateOrder(Long id, OrderRequestDto dto, String correlationId) {
        return given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + jwtToken)
                .header("X-Correlation-Id", correlationId)
                .body(dto)
                .put(ORDER + "/" + id);
    }

    public Response patchOrder(Long id, Object updates, String correlationId) {
        return given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + jwtToken)
                .header("X-Correlation-Id", correlationId)
                .body(updates)
                .patch(ORDER + "/" + id);
    }

    public Response deleteOrder(Long id, String correlationId) {
        return given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + jwtToken)
                .header("X-Correlation-Id", correlationId)
                .delete(ORDER + "/" + id);
    }

    public Response hardDeleteOrder(Long id, String correlationId) {
        return given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + jwtToken)
                .header("X-Correlation-Id", correlationId)
                .delete(HARD_DELETE_ORDER + "/" + id);
    }
}