package org.example.ui.cucumber.hooks;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class UiHooks {
    @Before
    public void setUp() {
        Configuration.browserSize = "1920x1080";
        Configuration.timeout = 3000;
        if ("true".equalsIgnoreCase(System.getenv("CI")) || Boolean.getBoolean("selenide.headless")) {
            Configuration.headless = true;
        }
    }

    @After
    public void tearDown(Scenario scenario) {

        if (scenario.isFailed()) {
            String screenshotPath = Selenide.screenshot("failed_" + scenario.getName());
            System.out.println("Screenshot saved: " + screenshotPath);
        }
    }
}
