package org.example.ui.cucumber.pages;

import com.codeborne.selenide.SelenideElement;
import org.example.ui.base.BaseUiPage;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.url;
import static com.codeborne.selenide.Condition.*;
import static org.example.ui.constants.UiConstants.*;

public class LoginPage extends BaseUiPage {
    @Override
    protected String getUrl() {
        return UI_LOGIN_PAGE;
    }

    public void shouldBeOpened() {
        webdriver().shouldHave(url(getUrl()));
    }

    public void enterField(String fieldName, String value) {
        getField(fieldName).setValue(value);
    }

    public void focusField(String fieldName) {
        getField(fieldName).click();
    }

    public void shouldHaveFieldPlaceholder(String fieldName, String expected) {
        getField(fieldName).shouldHave(attribute("placeholder", expected));
    }

    public void shouldHaveFieldValue(String fieldName, String expectedValue) {
        getField(fieldName).shouldHave(value(expectedValue));
    }

    public void shouldBePasswordMasked() {
        getField("password").shouldHave(attribute("type", "password"));
    }

    public void login(String user, String pass) {
        enterField("login", user);
        enterField("password", pass);
        getLoginButton().click();
    }

    public String getErrorMessage() {
        return $("[data-test='error']").getText();
    }

    public SelenideElement getLoginButton() {
        return $("#login-button");
    }

    private SelenideElement getField(String fieldName) {
        return switch (fieldName.toLowerCase()) {
            case "login", "username" -> $("#user-name");
            case "password" -> $("#password");
            default -> throw new IllegalArgumentException("Unknown field: " + fieldName);
        };
    }
}