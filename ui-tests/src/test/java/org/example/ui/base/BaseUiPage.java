package org.example.ui.base;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

public abstract class BaseUiPage {
    public void open() {
        com.codeborne.selenide.Selenide.open(getUrl());
    }

    protected abstract String getUrl();

    public abstract void shouldBeOpened();

    public abstract SelenideElement getElement(String elementName);

    public ElementsCollection getAllProducts() {
        throw new UnsupportedOperationException("Not implemented for this page");
    }

    public SelenideElement getProductByName(String productName) {
        throw new UnsupportedOperationException("Not implemented for this page");
    }

    public void shouldHaveButtonTextForProduct(String productName, String expectedText) {
        throw new UnsupportedOperationException("Not implemented for this page");
    }

    public void shouldHaveProductPropertiesAtIndex(int index, String name, String price) {
        throw new UnsupportedOperationException("Not implemented for this page");
    }

    public void shouldHaveItemsInCart(int expectedCount) {
        throw new UnsupportedOperationException("Not implemented for this page");
    }

    public void shouldHaveProductsQuantity(int expectedQuantityOfProducts) {
        throw new UnsupportedOperationException("Not implemented for this page");
    }

    public void addProductToCart(String productName) {
        throw new UnsupportedOperationException("Not implemented for this page");
    }

    public void removeProductFromCart(String productName) {
        throw new UnsupportedOperationException("Not implemented for this page");
    }

    public int getCartItemsQuantity() {
        throw new UnsupportedOperationException("Not implemented for this page");
    }
}