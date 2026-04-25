package org.example.services;

import lombok.extern.slf4j.Slf4j;
import org.example.clients.AuthApiClient;
import org.example.clients.OrderApiClient;
import org.example.models.LoginRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ApiAuthService {

    @Autowired
    private AuthApiClient authApiClient;
    @Autowired
    private OrderApiClient orderApiClient;
    @Autowired
    private ApiOrderService apiOrderService;

    public String login(String username, String password) {
        LoginRequestDto loginRequest = apiOrderService.buildLoginRequest(username, password);
        String token = authApiClient.login(loginRequest);
        if (token == null) {
            throw new AssertionError("Token was not received during login!");
        }
        log.info("Token: {}", token);
        orderApiClient.setJwtToken(token);
        return token;
    }
}