package org.example.ui.cucumber.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.example.ui.base.BaseUiPage;
import org.openqa.selenium.By;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.url;
import static org.example.ui.constants.UiConstants.UI_MAIN_PAGE;

public class MainPage extends BaseUiPage {

    private static final By MAIN_MENU = By.id("react-burger-menu-btn");
    private static final By LOGOUT_OPTION = By.id("logout_sidebar_link");
    private static final By CART_BUTTON = By.cssSelector("[data-test='shopping-cart-link']");
    private static final By ADD_TO_CART_BUTTON = By.id("add-to-cart-sauce-labs-bolt-t-shirt");
    private static final By CART_BADGE = By.cssSelector("[data-test='shopping-cart-badge']");
    private static final By PRODUCT_ITEMS = By.cssSelector(".inventory_item");

    private static final String PRODUCT_NAME = ".inventory_item_name";
    private static final String PRODUCT_PRICE = ".inventory_item_price";
    private static final String PRODUCT_BUTTON = "button";

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
    public SelenideElement getProductByName(String productName) {
        String normalizedName = productName.trim().toLowerCase();
        for (SelenideElement product : getAllProducts()) {
            String cardName = product.$(PRODUCT_NAME).getText().trim().toLowerCase();
            if (cardName.equals(normalizedName)) {
                return product;
            }
        }
        throw new IllegalArgumentException("Product not found: " + productName);
    }

    @Override
    public void shouldHaveButtonTextForProduct(String productName, String expectedText) {
        String normalizedExpected = expectedText.trim().replaceAll("\\s+", " ").toLowerCase();
        String actual = getProductByName(productName).$(PRODUCT_BUTTON).getText().trim().replaceAll("\\s+", " ").toLowerCase();
        if (!actual.equals(normalizedExpected)) {
            throw new AssertionError("Button text mismatch! Expected: '" + normalizedExpected + "', Actual: '" + actual + "'");
        }
    }

    @Override
    public void shouldHaveProductPropertiesAtIndex(int index, String name, String price) {
        ElementsCollection allDisplayedProducts = getAllProducts();
        allDisplayedProducts.get(index).$(PRODUCT_NAME).shouldHave(text(name));
        allDisplayedProducts.get(index).$(PRODUCT_PRICE).shouldHave(text(price));
    }

    @Override
    public void shouldHaveItemsInCart(int expectedQuantity) {
        $(CART_BADGE).shouldHave(text(String.valueOf(expectedQuantity)));
    }

    @Override
    public void shouldHaveProductsQuantity(int expectedQuantityOfProducts) {
        getAllProducts().shouldHave(size(expectedQuantityOfProducts));
    }

    @Override
    public void addProductToCart(String productName) {
        SelenideElement addProductButton = getProductByName(productName).$(PRODUCT_BUTTON);
        addProductButton.shouldHave(text("Add to cart")).click();
    }

    @Override
    public void removeProductFromCart(String productName) {
        SelenideElement removeProductButton = getProductByName(productName).$(PRODUCT_BUTTON);
        removeProductButton.shouldHave(text("Remove")).click();
    }

    @Override
    public int getCartItemsQuantity() {
        SelenideElement cartBadge = $(CART_BADGE);
        if (cartBadge.exists()) {
            return Integer.parseInt(cartBadge.getText().trim());
        }
        return 0;
    }
}