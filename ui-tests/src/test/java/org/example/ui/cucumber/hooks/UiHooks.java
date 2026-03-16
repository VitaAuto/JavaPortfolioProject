package org.example.ui.cucumber.hooks;

import com.codeborne.selenide.Configuration;
import io.cucumber.java.Before;
import org.example.ui.browser.BrowserFactory;


public class UiHooks {
    @Before
    public void setUp() {
        Configuration.browserSize = "1920x1080";
        Configuration.timeout = 3000;

        String browser = Configuration.browser != null ? Configuration.browser : "chrome";

        Configuration.browserCapabilities = BrowserFactory.getCapabilities(browser);

        if ("true".equalsIgnoreCase(System.getenv("CI")) || Boolean.getBoolean("selenide.headless")) {
            Configuration.headless = true;
        }
    }
}