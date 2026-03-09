package org.example.ui.base;

public abstract class BaseUiPage {
    public void open() {
        com.codeborne.selenide.Selenide.open(getUrl());
    }

    protected abstract String getUrl();

    public abstract void shouldBeOpened();
}