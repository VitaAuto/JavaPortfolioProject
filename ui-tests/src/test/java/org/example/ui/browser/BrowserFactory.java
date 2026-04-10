package org.example.ui.browser;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public class BrowserFactory {
    public static MutableCapabilities getCapabilities(String browser) {
        switch (browser.toLowerCase()) {
            case "chrome" -> {
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--incognito");
                // chromeOptions.addArguments("--disable-extensions");
                // chromeOptions.addArguments("--start-maximized");
                chromeOptions.addArguments("--headless=new");
                return chromeOptions;
            }
            case "firefox" -> {
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addPreference("browser.privatebrowsing.autostart", true);
                return firefoxOptions;
            }
            case "edge" -> {
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("--inprivate");
                return edgeOptions;
            }
            default -> throw new IllegalArgumentException("Unknown browser: " + browser);
        }
    }
}