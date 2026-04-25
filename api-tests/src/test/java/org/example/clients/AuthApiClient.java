package org.example.clients;

import io.restassured.response.Response;
import org.example.models.LoginRequestDto;
import org.springframework.stereotype.Component;

import static io.restassured.RestAssured.given;
import static org.example.constants.ApiConstants.LOGIN;

@Component
public class AuthApiClient {
    public String login(LoginRequestDto loginRequestDto) {
        Response response = given()
                .contentType("application/json")
                .body(loginRequestDto)
                .post(LOGIN);
        return response.jsonPath().getString("token");
    }
}