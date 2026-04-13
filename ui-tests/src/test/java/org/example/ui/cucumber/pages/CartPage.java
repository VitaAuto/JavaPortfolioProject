package org.example.ui.cucumber.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.example.ui.base.BaseUiPage;
import org.openqa.selenium.By;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.text;
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

    private static final String CART_ITEM_NAME = ".inventory_item_name";
    private static final String CART_ITEM_PRICE = ".inventory_item_price";
    private static final String CART_ITEM_BUTTON = "button";

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
    public SelenideElement getProductByName(String productName) {
        String normalizedName = productName.trim().toLowerCase();
        for (SelenideElement product : getAllProducts()) {

            String cardName = product.$(CART_ITEM_NAME).getText().trim().toLowerCase();
            if (cardName.equals(normalizedName)) {
                return product;
            }
        }
        throw new IllegalArgumentException("Product not found: " + productName);
    }

    @Override
    public void shouldHaveButtonTextForProduct(String productName, String expectedText) {
        String normalizedExpected = expectedText.trim().replaceAll("\\s+", " ").toLowerCase();
        String actual = getProductByName(productName).$(CART_ITEM_BUTTON).getText().trim().replaceAll("\\s+", " ").toLowerCase();
        if (!actual.equals(normalizedExpected)) {
            throw new AssertionError("Button text mismatch! Expected: '" + normalizedExpected + "', Actual: '" + actual + "'");
        }
    }

    @Override
    public void shouldHaveProductPropertiesAtIndex(int index, String name, String price) {
        ElementsCollection cartItems = getAllProducts();
        cartItems.get(index).$(CART_ITEM_NAME).shouldHave(text(name));
        cartItems.get(index).$(CART_ITEM_PRICE).shouldHave(text(price));
    }

    @Override
    public void shouldHaveItemsInCart(int expectedQuantity) {
        getAllProducts().shouldHave(size(expectedQuantity));

        int actualProducts = getAllProducts().size();
        int badgeCount = getCartItemsQuantity();

        if (actualProducts != badgeCount) {
            throw new AssertionError("Quantity of products on the page (" + actualProducts +
                    ") does not match the quantity in the cart badge (" + badgeCount + ")");
        }
        if (actualProducts != expectedQuantity) {
            throw new AssertionError("Expected Quantity of products: " + expectedQuantity +
                    ", but found on the page: " + actualProducts);
        }
    }

    @Override
    public void shouldHaveProductsQuantity(int expectedQuantityOfProducts) {
        getAllProducts().shouldHave(size(expectedQuantityOfProducts));
    }

    @Override
    public void removeProductFromCart(String productName) {
        SelenideElement button = getProductByName(productName).$(CART_ITEM_BUTTON);
        button.shouldHave(text("Remove"));
        button.click();
    }


    @Override
    public int getCartItemsQuantity() {
        SelenideElement badge = $(CART_BADGE);
        if (badge.exists()) {
            return Integer.parseInt(badge.getText().trim());
        }
        return 0;
    }
}