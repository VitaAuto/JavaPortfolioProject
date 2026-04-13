package org.example.ui.cucumber.pages;

import com.codeborne.selenide.SelenideElement;
import org.example.ui.base.BaseUiPage;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.url;
import static org.example.ui.constants.UiConstants.UI_LOGIN_PAGE;

public class LoginPage extends BaseUiPage {
    private static final By USERNAME_FIELD = By.id("user-name");
    private static final By PASSWORD_FIELD = By.id("password");
    private static final By LOGIN_BUTTON = By.id("login-button");
    private static final By LOGIN_ERROR = By.cssSelector(".error-message-container");

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
            case "username field" -> $(USERNAME_FIELD);
            case "password field" -> $(PASSWORD_FIELD);
            case "login button" -> $(LOGIN_BUTTON);
            case "login error" -> $(LOGIN_ERROR);
            default -> throw new IllegalArgumentException("Unknown element: " + elementName);
        };
    }
}