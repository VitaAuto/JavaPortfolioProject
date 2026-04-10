package org.example.ui.cucumber.pages;

import com.codeborne.selenide.SelenideElement;
import org.example.ui.base.BaseUiPage;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.url;
import static org.example.ui.constants.UiConstants.UI_LOGIN_PAGE;

public class LoginPage extends BaseUiPage {
    @Override
    protected String getUrl() {
        return UI_LOGIN_PAGE;
    }

    @Override
    public void shouldBeOpened() {
        webdriver().shouldHave(url(getUrl()));
    }

    @Override
    public SelenideElement getElement(String elementName) {
        return switch (elementName.toLowerCase()) {
            case "username field" -> $("#user-name");
            case "password field" -> $("#password");
            case "login button" -> $("#login-button");
            case "login error message" -> $(".error-message-container");
            default -> throw new IllegalArgumentException("Unknown element: " + elementName);
        };
    }
}