package org.example.ui.cucumber.pages;

import com.codeborne.selenide.ElementsCollection;
import org.example.ui.base.BaseUiPage;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.url;
import static org.example.ui.constants.UiConstants.*;

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

    public void shouldHaveProductPropertiesAtIndex(int index, String expectedName, String expectedPrice) {
        ElementsCollection allDisplayedProducts = getAllProducts();
        allDisplayedProducts.get(index).$(".inventory_item_name").shouldHave(text(expectedName));
        allDisplayedProducts.get(index).$(".inventory_item_price").shouldHave(text(expectedPrice));
    }
}