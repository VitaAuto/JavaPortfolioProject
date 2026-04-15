package org.example.ui.base;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.text;
import static org.example.ui.constants.ProductCardLocators.*;

public abstract class BaseUiPage {
    public void open() {
        com.codeborne.selenide.Selenide.open(getUrl());
    }

    protected abstract String getUrl();

    public abstract void shouldBeOpened();

    public abstract SelenideElement getElement(String elementName);

    public ElementsCollection getAllProducts() {throw new UnsupportedOperationException("Not implemented for this page");
    }

    public SelenideElement getProductByName(String productName) {
        String normalizedProductName = productName.trim().toLowerCase();
        for (SelenideElement product : getAllProducts()) {

            String productCardName = product.$(NAME).getText().trim().toLowerCase();
            if (productCardName.equals(normalizedProductName)) {
                return product;
            }
        }
        throw new IllegalArgumentException("Product not found: " + productName);
    }

    public void shouldHaveButtonTextForProduct(String productName, String expectedText) {
        String normalizedExpectedText = expectedText.trim().replaceAll("\\s+", " ").toLowerCase();
        String actualText = getProductByName(productName).$(BUTTON).getText().trim().replaceAll("\\s+", " ").toLowerCase();
        if (!actualText.equals(normalizedExpectedText)) {
            throw new AssertionError("Button text mismatch! Expected: '" + normalizedExpectedText + "', Actual: '" + actualText + "'");
        }
    }

    public void shouldHaveProductProperties(int index, String name, String price, String description, String button) {
        ElementsCollection allDisplayedProducts = getAllProducts();
        SelenideElement product = allDisplayedProducts.get(index);
        product.$(NAME).shouldHave(text(name));
        product.$(PRICE).shouldHave(text(price));
        product.$(DESCRIPTION).shouldHave(text(description));
        product.$(BUTTON).shouldHave(text(button));
    }

    public void shouldHaveQuantityOfItemsInCart(int expectedQuantityOfItemsInCart) {throw new UnsupportedOperationException("Not implemented for this page");
    }

    public void shouldHaveProductsQuantity(int expectedQuantityOfProducts) {
        getAllProducts().shouldHave(size(expectedQuantityOfProducts));
    }

    public void addProductToCart(String productName) {throw new UnsupportedOperationException("Not implemented for this page");
    }

    public void removeProductFromCart(String productName) {
        SelenideElement removeProductButton = getProductByName(productName).$(BUTTON);
        removeProductButton.shouldHave(text("Remove")).click();
    }

    public int getCartItemsQuantity() {throw new UnsupportedOperationException("Not implemented for this page");}
}