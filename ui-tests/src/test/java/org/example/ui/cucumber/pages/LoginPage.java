package org.example.ui.cucumber.pages;

import com.codeborne.selenide.SelenideElement;
import org.example.ui.base.BaseUiPage;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.url;
import static org.example.ui.constants.UiConstants.*;

public class LoginPage extends BaseUiPage {
    private SelenideElement username = $("#user-name");
    private SelenideElement password = $("#password");
    private SelenideElement loginBtn = $("#login-button");

    @Override
    protected String getUrl() {
        return UI_LOGIN_PAGE;
    }

    public String getErrorMessage() {
        return $("[data-test='error']").getText();
    }

    @Override
    public void shouldBeOpened()
    {
        webdriver().shouldHave(url(getUrl()));
    }

    public void login(String user, String pass) {
        username.setValue(user);
        password.setValue(pass);
        loginBtn.click();
    }
}