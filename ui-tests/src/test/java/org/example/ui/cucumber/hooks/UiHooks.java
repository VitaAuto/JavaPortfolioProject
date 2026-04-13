package org.example.ui.cucumber.hooks;

import com.codeborne.selenide.Configuration;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.example.ui.browser.BrowserFactory;
import org.example.ui.context.UiScenarioContext;
import org.example.ui.cucumber.pages.*;

public class UiHooks {
    private final UiScenarioContext context;

    public UiHooks(UiScenarioContext context) {
        this.context = context;
    }

    @Before
    public void setUp() {
        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
        Configuration.timeout = 3000;

        Configuration.browserCapabilities = BrowserFactory.getCapabilities(Configuration.browser);

        context.setPage("login", new LoginPage());
        context.setPage("main", new MainPage());
        context.setPage("cart", new CartPage());
        context.setPage("checkout", new CheckoutPage());
        context.setPage("saucelabs", new SaucelabsPage());
    }

    @After
    public void tearDown() {
        context.clear();
        com.codeborne.selenide.Selenide.closeWebDriver();
    }
}