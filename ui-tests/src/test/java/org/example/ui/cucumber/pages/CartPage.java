package org.example.ui.cucumber.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.example.ui.base.BaseUiPage;
import org.openqa.selenium.By;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.url;
import static org.example.ui.constants.UiConstants.UI_CART_PAGE;

public class CartPage extends BaseUiPage {

    private static final By CART_PAGE_TITLE = By.cssSelector("[data-test='title']");
    private static final By CART_BUTTON = By.id("shopping-cart-link");
    private static final By CONTINUE_BUTTON = By.id("continue-shopping");
    private static final By CHECKOUT_BUTTON = By.id("checkout");
    private static final By CART_BADGE = By.cssSelector("[data-test='shopping-cart-badge']");
    private static final By CART_ITEMS = By.cssSelector(".cart_item");

    @Override
    protected String getUrl() { return UI_CART_PAGE; }

    @Override
    public void shouldBeOpened() {
        webdriver().shouldHave(url(getUrl()));
    }

    @Override
    public SelenideElement getElement(String elementName) {
        return switch (elementName.toLowerCase()) {
            case "cart page title" -> $(CART_PAGE_TITLE);
            case "cart button" -> $(CART_BUTTON);
            case "continue button" -> $(CONTINUE_BUTTON);
            case "checkout button" -> $(CHECKOUT_BUTTON);
            default -> throw new IllegalArgumentException("Unknown element: " + elementName);
        };
    }

    @Override
    public ElementsCollection getAllProducts() {
        return $$(CART_ITEMS);
    }

    @Override
    public void shouldHaveQuantityOfItemsInCart(int expectedQuantityOfItemsInCart) {
        getAllProducts().shouldHave(size(expectedQuantityOfItemsInCart));

        int actualQuantityOfItemsInCart = getAllProducts().size();
        int cartQuantity = getCartItemsQuantity();

        if (actualQuantityOfItemsInCart != cartQuantity) {
            throw new AssertionError("Quantity of products on the page (" + actualQuantityOfItemsInCart +
                    ") does not match the quantity in the cart (" + cartQuantity + ")");
        }
        if (actualQuantityOfItemsInCart != expectedQuantityOfItemsInCart) {
            throw new AssertionError("Expected quantity of products on the page: " + expectedQuantityOfItemsInCart +
                    ", but found: " + actualQuantityOfItemsInCart);
        }
    }

    @Override
    public int getCartItemsQuantity() {
        SelenideElement cartQuantity = $(CART_BADGE);
        if (cartQuantity.exists()) {
            return Integer.parseInt(cartQuantity.getText().trim());
        }
        return 0;
    }
}