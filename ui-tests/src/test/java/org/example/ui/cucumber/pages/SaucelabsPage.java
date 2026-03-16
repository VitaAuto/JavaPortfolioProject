package org.example.ui.cucumber.pages;

import org.example.ui.base.BaseUiPage;

import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.url;
import static org.example.ui.constants.UiConstants.UI_SAUCELABS_PAGE;

public class SaucelabsPage extends BaseUiPage {
    @Override
    protected String getUrl() {
        return UI_SAUCELABS_PAGE;
    }

    @Override
    public void shouldBeOpened() {
        webdriver().shouldHave(url(getUrl()));
    }
}
