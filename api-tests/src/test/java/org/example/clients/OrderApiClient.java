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

    public Response createOrder(OrderRequestDto dto) {
        return given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + jwtToken)

                .body(dto)
                .post(ORDER);
    }

    public Response getOrder(Long id) {
        return given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + jwtToken)
                .get(ORDER + "/" + id);
    }

    public Response updateOrder(Long id, OrderRequestDto dto) {
        return given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + jwtToken)
                .body(dto)
                .put(ORDER + "/" + id);
    }

    public Response patchOrder(Long id, Object updates) {
        return given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + jwtToken)
                .body(updates)
                .patch(ORDER + "/" + id);
    }

    public Response deleteOrder(Long id) {
        return given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + jwtToken)
                .delete(ORDER + "/" + id);
    }

    public Response hardDeleteOrder(Long id) {
        return given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + jwtToken)
                .delete(HARD_DELETE_ORDER + "/" + id);
    }
}