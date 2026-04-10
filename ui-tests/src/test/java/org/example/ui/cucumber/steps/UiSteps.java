package org.example.ui.cucumber.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.ui.context.UiScenarioContext;
import org.example.ui.cucumber.pages.MainPage;

import java.util.List;
import java.util.Map;

import static com.codeborne.selenide.Condition.*;

public class UiSteps {
    private final UiScenarioContext context;

    public UiSteps(UiScenarioContext context) {
        this.context = context;
    }

    @Given("user navigates to {string} page")
    public void user_navigates_to_page(String pageName) {
        context.setCurrentPage(pageName);
        context.getCurrentPage().open();
    }

    @When("user logs in as {string}")
    public void user_logs_in_as(String role) {
        String username;
        String password;
        switch (role.toLowerCase()) {
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
        context.getCurrentPage().getElement("username field").setValue(username);
        context.getCurrentPage().getElement("password field").setValue(password);
        context.getCurrentPage().getElement("login button").click();
    }

    @When("user enters {string} into {string}")
    public void user_enters_value_into_element(String value, String elementName) {
        context.getCurrentPage().getElement(elementName).shouldBe(visible).setValue(value);
    }

    @When("user focuses on {string}")
    public void user_focuses_on_element(String elementName) {
        context.getCurrentPage().getElement(elementName).shouldBe(visible).click();
    }

    @When("user clicks {string}")
    public void user_clicks_element(String elementName) {
        context.getCurrentPage().getElement(elementName).shouldBe(visible).click();
    }

    @When("user tries to log in")
    public void user_tries_to_log_in() {
        context.getCurrentPage().getElement("login button").shouldBe(visible).click();
    }

    @Then("{string} page is open")
    public void page_is_open(String pageName) {
        context.getPage(pageName).shouldBeOpened();
        context.setCurrentPage(pageName);
    }

    @Then("{string} should be visible")
    public void element_should_be_visible(String elementName) {
        context.getCurrentPage().getElement(elementName).shouldBe(visible);
    }

    @Then("{string} should have text {string}")
    public void element_should_have_text(String elementName, String expectedText) {
        context.getCurrentPage().getElement(elementName).shouldHave(text(expectedText));
    }

    @Then("login field placeholder is {string}")
    public void login_field_placeholder_is(String expectedPlaceholder) {
        context.getCurrentPage().getElement("Username field").shouldHave(attribute("placeholder", expectedPlaceholder));
    }

    @Then("password field placeholder is {string}")
    public void password_field_placeholder_is(String expectedPlaceholder) {
        context.getCurrentPage().getElement("Password field").shouldHave(attribute("placeholder", expectedPlaceholder));
    }

    @Then("login field value is {string}")
    public void login_field_value_is(String expectedValue) {
        context.getCurrentPage().getElement("Username field").shouldHave(value(expectedValue));
    }

    @Then("password field is masked")
    public void password_field_is_masked() {
        context.getCurrentPage().getElement("Password field").shouldHave(attribute("type", "password"));
    }

    @Then("\"Login\" page error message contains {string}")
    public void login_page_error_message_contains(String expectedMessage) {
        context.getCurrentPage().getElement("Login error message").shouldHave(text(expectedMessage));
    }

    // MainPage specific steps
    @Then("{int} items are shown")
    public void items_are_shown(int expectedQuantityOfProducts) {
        ((MainPage) context.getPage("main")).shouldHaveProductsQuantity(expectedQuantityOfProducts);
    }

    @Then("the following products are shown in order:")
    public void the_following_products_are_shown_in_order(DataTable table) {
        List<Map<String, String>> products = table.asMaps();
        for (Map<String, String> product : products) {
            int position = Integer.parseInt(product.get("position")) - 1;
            String name = product.get("name");
            String price = product.get("price");
            context.getCurrentPage().shouldHaveProductPropertiesAtIndex(position, name, price);
        }
    }
}