package org.example.ui.context;

import org.example.ui.base.BaseUiPage;

import java.util.HashMap;
import java.util.Map;

public class UiScenarioContext {
    private final Map<String, BaseUiPage> pages = new HashMap<>();
    private BaseUiPage currentPage;
    private final Map<String, Object> data = new HashMap<>();

    public void setPage(String keyName, BaseUiPage valuePage) {
        pages.put(keyName.toLowerCase(), valuePage);
    }

    public BaseUiPage getPage(String keyName) {
        BaseUiPage page = pages.get(keyName.toLowerCase());
        if (page == null) {
            throw new IllegalStateException("Page with name '" + keyName + "' is not set in context!");
        }
        return page;
    }

    public void setCurrentPage(String keyName) {
        this.currentPage = getPage(keyName);
    }

    public BaseUiPage getCurrentPage() {
        if (currentPage == null) {
            throw new IllegalStateException("Current page is not set!");
        }
        return currentPage;
    }

    public void put(String key, Object value) {
        data.put(key, value);
    }

    public <T> T get(String key, Class<T> clazz) {
        Object value = data.get(key);
        if (value == null) {
            throw new IllegalStateException("No value found in context for key: " + key);
        }
        return clazz.cast(value);
    }

    public void clear() {
        pages.clear();
        currentPage = null;
        data.clear();
    }
}