package org.example.ui.cucumber.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.example.ui.base.BaseUiPage;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.url;
import static org.example.ui.constants.ProductCardLocators.BUTTON;
import static org.example.ui.constants.UiConstants.UI_MAIN_PAGE;

public class MainPage extends BaseUiPage {

    private static final By MAIN_MENU = By.id("react-burger-menu-btn");
    private static final By LOGOUT_OPTION = By.id("logout_sidebar_link");
    private static final By CART_BUTTON = By.cssSelector("[data-test='shopping-cart-link']");
    private static final By ADD_TO_CART_BUTTON = By.id("add-to-cart-sauce-labs-bolt-t-shirt");
    private static final By CART_BADGE = By.cssSelector("[data-test='shopping-cart-badge']");
    private static final By PRODUCT_ITEMS = By.cssSelector(".inventory_item");


    @Override
    protected String getUrl() { return UI_MAIN_PAGE; }

    @Override
    public void shouldBeOpened() {
        webdriver().shouldHave(url(getUrl()));
    }

    @Override
    public SelenideElement getElement(String elementName) {
        return switch (elementName.toLowerCase()) {
            case "main menu" -> $(MAIN_MENU);
            case "main menu logout option" -> $(LOGOUT_OPTION);
            case "cart button" -> $(CART_BUTTON);
            case "add to cart button" -> $(ADD_TO_CART_BUTTON);
            default -> throw new IllegalArgumentException("Unknown element: " + elementName);
        };
    }

    @Override
    public ElementsCollection getAllProducts() {
        return $$(PRODUCT_ITEMS);
    }

    @Override
    public void shouldHaveQuantityOfItemsInCart(int expectedQuantityOfItemsInCart) {
        $(CART_BADGE).shouldHave(text(String.valueOf(expectedQuantityOfItemsInCart)));
    }

    @Override
    public void addProductToCart(String productName) {
        SelenideElement addProductButton = getProductByName(productName).$(BUTTON);
        addProductButton.shouldHave(text("Add to cart")).click();
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