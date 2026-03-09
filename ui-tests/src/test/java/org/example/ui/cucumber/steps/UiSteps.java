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
            default ->
                throw new IllegalArgumentException("Unknown page: " + pageName);
        }
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
            //case "saucelabs" -> saucelabsPage.shouldBeOpened();
            //case "cart" -> cartPage.shouldBeOpened();
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
}