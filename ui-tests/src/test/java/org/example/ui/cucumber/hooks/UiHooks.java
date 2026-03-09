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
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            Selenide.screenshot("failed_" + scenario.getName());
        }
        Selenide.closeWebDriver();
    }
}
