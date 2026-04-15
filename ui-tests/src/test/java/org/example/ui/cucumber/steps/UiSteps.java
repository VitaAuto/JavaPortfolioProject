package org.example.ui.cucumber.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.ui.base.BaseUiPage;
import org.example.ui.context.UiScenarioContext;

import java.util.List;
import java.util.Map;

import static com.codeborne.selenide.Condition.*;

public class UiSteps {
    private final UiScenarioContext context;

    public UiSteps(UiScenarioContext context) {
        this.context = context;
    }

    @Given("user navigates to {string} page")
    public void userNavigatesToPage(String pageName) {
        context.setCurrentPage(pageName);
        context.getCurrentPage().open();
    }

    @When("user logs in as {string}")
    public void userLogsInAs(String role) {
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
    public void userEntersValueIntoElement(String value, String elementName) {
        context.getCurrentPage().getElement(elementName).shouldBe(visible).setValue(value);
    }

    @When("user clicks {string}")
    @When("user focuses on {string}")
    public void userClicksElement(String elementName) {
        context.getCurrentPage().getElement(elementName).shouldBe(visible).click();
    }

    @Then("{string} page is open")
    public void pageIsOpen(String pageName) {
        context.getPage(pageName).shouldBeOpened();
        context.setCurrentPage(pageName);
    }

    @Then("{string} placeholder is {string}")
    public void fieldPlaceholderIs(String elementName, String expectedPlaceholder) {
        context.getCurrentPage().getElement(elementName).shouldHave(attribute("placeholder", expectedPlaceholder));
    }

    @Then("{string} value is {string}")
    public void fieldValueIs(String elementName, String expectedValue) {
        context.getCurrentPage().getElement(elementName).shouldHave(value(expectedValue));
    }

    @Then("{string} is masked")
    public void fieldIsMasked(String elementName) {
        context.getCurrentPage().getElement(elementName).shouldHave(attribute("type", "password"));
    }

    @Then("{string} message contains {string}")
    public void errorMessageContains(String elementName, String expectedMessage) {
        context.getCurrentPage().getElement(elementName).shouldHave(text(expectedMessage));
    }

    @Then("{int} items are shown")
    public void productItemsAreShown(int expectedQuantityOfProducts) {
        context.getCurrentPage().shouldHaveProductsQuantity(expectedQuantityOfProducts);
    }

    @Then("^the following product(?:s)? is/are shown with properties:$")
    public void theFollowingProductsAreShownInOrder(DataTable table) {
        List<Map<String, String>> products = table.asMaps();
        for (Map<String, String> product : products) {
            int position = Integer.parseInt(product.get("position")) - 1;
            String name = product.get("name");
            String price = product.get("price");
            String description = product.get("description");
            String button = product.get("button");
            context.getCurrentPage().shouldHaveProductProperties(position, name, price, description, button);
        }
    }

    @When("user {word} {string} {word} cart")
    public void userChangesProductsInCart(String action, String productName, String preposition) {
        BaseUiPage page = context.getCurrentPage();
        switch (action.toLowerCase()) {
            case "adds" -> page.addProductToCart(productName);
            case "removes" -> page.removeProductFromCart(productName);
            default -> throw new IllegalArgumentException("Unknown action: " + action + ". Use 'adds' or 'removes'.");
        }
    }

    @Then("{string} product should have button with text {string}")
    public void productShouldHaveButtonWithText(String productName, String buttonText) {
        context.getCurrentPage().shouldHaveButtonTextForProduct(productName, buttonText);
    }

    @Then("quantity of shown items in the cart is {int}")
    public void quantityOfShownItemsInTheCart(int expectedQuantity) {
        context.getCurrentPage().shouldHaveQuantityOfItemsInCart(expectedQuantity);
    }

    @And("user remembers current quantity of shown items in the cart")
    public void userRemembersCurrentCartItemsQuantity() {
        int currentCartItemsQuantity = context.getCurrentPage().getCartItemsQuantity();
        context.put("currentCartItemsQuantity", currentCartItemsQuantity);
    }

    @Then("quantity of shown items in the cart is {word} by {int}")
    public void quantityOfShownItemsInTheCartIsChangedBy(String direction, int difference) {
        int rememberedCartItemsQuantity = context.get("currentCartItemsQuantity", Integer.class);
        int currentCartItemsQuantity = context.getCurrentPage().getCartItemsQuantity();

        switch (direction.toLowerCase()) {
            case "increased" -> {
                if (currentCartItemsQuantity != rememberedCartItemsQuantity + difference) {
                    throw new AssertionError("Cart items quantity not increased by " + difference + " ");
                }
            }
            case "decreased" -> {
                if (currentCartItemsQuantity != rememberedCartItemsQuantity - difference) {
                    throw new AssertionError("Cart items quantity not decreased by " + difference + " ");
                }
            }
            default -> throw new IllegalArgumentException("Unknown direction: " + difference + " ");
        }
    }
}