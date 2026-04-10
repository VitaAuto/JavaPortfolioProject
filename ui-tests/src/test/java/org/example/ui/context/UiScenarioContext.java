package org.example.ui.context;

import org.example.ui.base.BaseUiPage;

import java.util.HashMap;
import java.util.Map;


public class UiScenarioContext {
    private final Map<String, BaseUiPage> pages = new HashMap<>();
    private BaseUiPage currentPage;

    public void setPage(String keyName, BaseUiPage valuePage) {
        pages.put(keyName.toLowerCase(), valuePage);
    }

    public BaseUiPage getPage(String keyName) {
        BaseUiPage page = pages.get(keyName.toLowerCase());
        if (page == null) {throw new IllegalStateException("Page with name '" + keyName + "' is not set in context!");}

        return page;
    }

    public void setCurrentPage(String keyName) {
        this.currentPage = getPage(keyName);
    }

    public BaseUiPage getCurrentPage() {
        if (currentPage == null)
        {throw new IllegalStateException("Current page is not set!");}

        return currentPage;
    }

    public void clear() {
        pages.clear();
        currentPage = null;
    }
}