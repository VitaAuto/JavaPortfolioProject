package org.example.ui.cucumber.pages;

import com.codeborne.selenide.ElementsCollection;
import org.example.ui.base.BaseUiPage;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.url;
import static org.example.ui.constants.UiConstants.UI_MAIN_PAGE;

public class MainPage extends BaseUiPage {
    @Override
    protected String getUrl() {
        return UI_MAIN_PAGE;
    }

    @Override
    public void shouldBeOpened() {
        webdriver().shouldHave(url(getUrl()));
    }

    public ElementsCollection getAllProducts() {return $$(".inventory_item");}

    public void shouldHaveProductsQuantity(int expectedQuantityOfProducts) {
        getAllProducts().shouldHave(size(expectedQuantityOfProducts));
    }
}