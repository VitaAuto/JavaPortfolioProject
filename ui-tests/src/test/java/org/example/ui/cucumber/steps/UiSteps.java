package org.example.ui.cucumber.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.ui.cucumber.pages.LoginPage;
import org.example.ui.cucumber.pages.MainPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UiSteps {
    private final LoginPage loginPage = new LoginPage();
    private final MainPage mainPage = new MainPage();

    @Given("user navigates to \"{word}\" page")
    public void user_navigates_to_page(String pageName) {
        switch (pageName.toLowerCase()) {
            case "login" -> loginPage.open();
            case "main" -> mainPage.open();
            default -> throw new IllegalArgumentException("Unknown page: " + pageName);
        }
    }

    @When("user enters {string} as {word}")
    public void user_enters_value_as_field(String value, String fieldName) {
        loginPage.enterField(fieldName, value);
    }

    @When("user focuses on {word} field")
    public void user_focuses_on_field(String fieldName) {
        loginPage.focusField(fieldName);
    }

    @When("user tries to log in")
    public void user_clicks_on_login_button() {
        loginPage.getLoginButton().click();
    }

    @When("user logs in as {string}")
    public void user_logs_in_as(String role) {
        String username;
        String password;
        switch (role) {
            case "standard" -> {
                username = System.getenv("STANDARD_LOGIN_USER");
                password = System.getenv("STANDARD_LOGIN_PASSWORD");
            }
            case "locked_out" -> {
                username = System.getenv("LOCKED_OUT_USER");
                password = System.getenv("LOCKED_OUT_PASSWORD");
            }
            default -> throw new IllegalArgumentException("Unknown role: " + role);
        }
        loginPage.login(username, password);
    }

    @Then("\"{word}\" page is open")
    public void page_is_open(String pageName) {
        switch (pageName.toLowerCase()) {
            case "login" -> loginPage.shouldBeOpened();
            case "main" -> mainPage.shouldBeOpened();
            default -> throw new IllegalArgumentException("Unknown page: " + pageName);
        }
    }

    @Then("\"{word}\" page error message contains {string}")
    public void page_error_message_contains(String pageName, String expectedMessage) {
        String actualMessage;
        switch (pageName.toLowerCase()) {
            case "login" -> actualMessage = loginPage.getErrorMessage();
            default -> throw new IllegalArgumentException("Unknown page: " + pageName);
        }
        assertTrue(actualMessage.contains(expectedMessage), "Actual error message: '" + actualMessage + "' does not contain expected: '" + expectedMessage + "'");
    }

    @Then("{word} field placeholder is {string}")
    public void field_placeholder_is(String fieldName, String expectedPlaceholder) {
        loginPage.shouldHaveFieldPlaceholder(fieldName, expectedPlaceholder);
    }

    @Then("{word} field value is {string}")
    public void field_value_is(String fieldName, String expectedValue) {
        loginPage.shouldHaveFieldValue(fieldName, expectedValue);
    }

    @Then("password field is masked")
    public void password_field_is_masked() {
        loginPage.shouldBePasswordMasked();
    }

    @Then("{int} items are shown")
    public void items_are_shown(int expectedQuantityOfProducts) {
        mainPage.shouldHaveProductsQuantity(expectedQuantityOfProducts);
    }

}