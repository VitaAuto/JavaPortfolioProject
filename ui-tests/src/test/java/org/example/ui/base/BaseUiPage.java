package org.example.ui.base;

import com.codeborne.selenide.SelenideElement;

public abstract class BaseUiPage {
    public void open() {
        com.codeborne.selenide.Selenide.open(getUrl());
    }

    protected abstract String getUrl();

    public abstract void shouldBeOpened();

    public abstract SelenideElement getElement(String elementName);

    public void focusField(String fieldName) {
        getElement(fieldName).click();
    }

    public void enterField(String fieldName, String value) {
        getElement(fieldName).setValue(value);
    }

    public void shouldHaveProductPropertiesAtIndex(int index, String name, String price) {
        throw new UnsupportedOperationException("Not implemented for this page");
    }
}