package org.example.ui.cucumber.pages;

import org.example.ui.base.BaseUiPage;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.url;
import static org.example.ui.constants.UiConstants.*;

public class CheckoutPage extends BaseUiPage {
    @Override
    protected String getUrl() {
        return UI_CHECKOUT_PAGE;
    }

    @Override
    public void shouldBeOpened() {
        webdriver().shouldHave(url(getUrl()));
    }

    public String getErrorMessage() {
        return $("[data-test='error']").getText();
    }

}
