package org.example.cucumber.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.example.base.BaseDbTest;
import org.example.clients.OrderApiClient;
import org.example.context.ApiScenarioContext;
import org.example.models.OrderRequestDto;
import org.example.models.OrderResponseDto;
import org.example.models.enums.OrderStatus;
import org.example.services.ApiAuthService;
import org.example.services.ApiOrderService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@Slf4j
public class OrderSteps {

    @Autowired
    private ApiScenarioContext context;
    @Autowired
    private BaseDbTest orderDb;
    @Autowired
    private OrderApiClient orderApiClient;
    @Autowired
    private ApiAuthService apiAuthService;
    @Autowired
    private ApiOrderService apiOrderService;

    @Given("user logs in with {word} username and {word} password")
    public void user_logs_in_with_username_and_password(String usernameType, String passwordType) {
        String username = apiOrderService.resolveUsername(usernameType);
        String password = apiOrderService.resolvePassword(passwordType);
        String token = apiAuthService.login(username, password);
        context.set("jwtToken", token);
    }

    @When("user tries to get all orders")
    public void user_tries_to_get_all_orders() {
        Response response = orderApiClient.getAllOrders();
        log.info("GET all orders response: {}", response.asString());
        context.set("response", response);
    }

    @When("user creates new order with username {string}, description {string}, and amount {string}")
    public void user_creates_new_order_with_params(String username, String description, String amountStr) {
        OrderRequestDto requestOrder = apiOrderService.buildOrderRequest(username, description, amountStr);
        log.info("POST create order request: {}", requestOrder);

        Response response = orderApiClient.createOrder(requestOrder);
        log.info("POST create order response: {}", response.asString());

        context.set("response", response);
        context.set("requestOrder", requestOrder);
        apiOrderService.saveOrderIdIfCreated(response, context);
    }

    @When("user tries to get order by id {string}")
    public void user_tries_to_get_order_by_id(String idStr) {
        Long id = apiOrderService.resolveId(idStr, context);
        log.info("GET order by id: {}", id);

        Response response = orderApiClient.getOrder(id);
        log.info("GET order by id response: {}", response.asString());
        context.set("response", response);
    }

    @When("user updates order with id {string} and username {string}, description {string}, and amount {double}")
    public void user_updates_order_with_params(String idStr, String username, String description, Double amount) {
        Long id = apiOrderService.resolveId(idStr, context);
        OrderRequestDto requestOrder = apiOrderService.buildOrderRequest(username, description, amount != null ? amount.toString() : null);
        log.info("PUT update order id: {}, request: {}", id, requestOrder);

        Response response = orderApiClient.updateOrder(id, requestOrder);
        log.info("PUT update order response: {}", response.asString());
        context.set("response", response);
    }

    @When("user partially updates fields of order with id {string}")
    public void user_partially_updates_fields_of_order_with_id(String idStr, DataTable dataTable) {
        Long id = apiOrderService.resolveId(idStr, context);
        Map<String, Object> updates = dataTable.asMap(String.class, Object.class);
        log.info("PATCH order id: {}, updates: {}", id, updates);

        Response response = orderApiClient.patchOrder(id, updates);
        log.info("PATCH order response: {}", response.asString());
        context.set("response", response);
    }

    @When("user deletes order with id {string}")
    public void user_deletes_order_with_id(String idStr) {
        Long id = apiOrderService.resolveId(idStr, context);
        log.info("DELETE order id: {}", id);

        Response response = orderApiClient.deleteOrder(id);
        log.info("DELETE order response: {}", response.asString());
        context.set("response", response);
    }

    @When("user hard deletes order with id {string}")
    public void user_hard_deletes_order_with_id(String idStr) {
        Long id = apiOrderService.resolveId(idStr, context);
        log.info("HARD DELETE order id: {}", id);

        Response response = orderApiClient.hardDeleteOrder(id);
        log.info("HARD DELETE order response: {}", response.asString());
        context.set("response", response);
    }

    @When("database table {string} is cleared")
    public void database_table_is_cleared(String tableName) {
        orderDb.clearTableAndAwait(tableName);
    }

    @Then("response should have status code {int}")
    public void response_should_have_status_code(int statusCode) {
        Response response = context.get("response", Response.class);
        Assertions.assertThat(response)
                .withFailMessage("No response found in scenario context")
                .isNotNull();
        log.info("Expected status code: {}, actual: {}", statusCode, response.getStatusCode());
        response.then().statusCode(statusCode);
    }

    @Then("^response should( not)? contain at least one order$")
    public void response_should_contain_at_least_one_order(String not) {
        Response response = context.get("response", Response.class);
        Assertions.assertThat(response)
                .withFailMessage("No response found in scenario context")
                .isNotNull();
        apiOrderService.assertOrdersPresence(response, not);
    }

    @Then("response should contain order with username {string}, status {orderStatus}, description {string}, and amount {double}")
    public void response_should_contain_order_with_params(String username, OrderStatus status, String description, Double amount) {
        Response response = context.get("response", Response.class);
        Assertions.assertThat(response)
                .withFailMessage("No response found in scenario context")
                .isNotNull();
        var order = response.as(OrderResponseDto.class);
        log.info("Asserting order: {}", order);
        Assertions.assertThat(order.getUsername()).isEqualTo(username);
        Assertions.assertThat(order.getStatus()).isEqualTo(status);
        Assertions.assertThat(order.getDescription()).isEqualTo(description);
        Assertions.assertThat(order.getAmount()).isEqualTo(amount);
    }

    @Then("response should contain order with id {string}")
    public void response_should_contain_order_with_id(String idStr) {
        Long id = apiOrderService.resolveId(idStr, context);
        Response response = context.get("response", Response.class);
        Assertions.assertThat(response)
                .withFailMessage("No response found in scenario context")
                .isNotNull();
        var order = response.as(OrderResponseDto.class);
        log.info("Asserting order by id: {}, order: {}", id, order);
        Assertions.assertThat(order.getId()).isEqualTo(id);
    }

    @Then("no order should have negative amount")
    public void no_order_should_have_negative_amount() {
        Response response = context.get("response", Response.class);
        Assertions.assertThat(response)
                .withFailMessage("No response found in scenario context")
                .isNotNull();
        var orders = response.jsonPath().getList("$", OrderResponseDto.class);
        boolean noNegative = orders.stream().noneMatch(o -> o.getAmount() < 0);
        log.info("No negative amount: {}", noNegative);
        Assertions.assertThat(noNegative).isTrue();
    }

    @Then("response should contain error message {string}")
    public void response_should_contain_error_message(String expectedMessage) {
        Response response = context.get("response", Response.class);
        Assertions.assertThat(response)
                .withFailMessage("No response found in scenario context")
                                .isNotNull();
        var errorMessage = response.jsonPath().getString("amount");
        log.info("Asserting error message: expected '{}', actual '{}'", expectedMessage, errorMessage);
        Assertions.assertThat(errorMessage).isEqualTo(expectedMessage);
    }
}