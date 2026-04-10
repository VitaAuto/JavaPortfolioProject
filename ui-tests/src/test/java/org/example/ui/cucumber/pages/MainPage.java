package org.example.ui.cucumber.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.example.ui.base.BaseUiPage;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
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

    public ElementsCollection getAllProducts() { return $$(".inventory_item"); }

    public void shouldHaveProductsQuantity(int expectedQuantityOfProducts) {
        getAllProducts().shouldHave(size(expectedQuantityOfProducts));
    }

    @Override
    public void shouldHaveProductPropertiesAtIndex(int index, String name, String price) {
        ElementsCollection allDisplayedProducts = getAllProducts();
        allDisplayedProducts.get(index).$(".inventory_item_name").shouldHave(text(name));
        allDisplayedProducts.get(index).$(".inventory_item_price").shouldHave(text(price));
    }

    @Override
    public SelenideElement getElement(String elementName) {
        return switch (elementName.toLowerCase()) {
            case "main menu" -> $("#react-burger-menu-btn");
            case "main manu logout option" -> $("#logout_sidebar_link");
            case "cart" -> $(".shopping_cart_link");
            default -> throw new IllegalArgumentException("Unknown element: " + elementName);
        };
    }
}