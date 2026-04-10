package org.example.cucumber.hooks;
import org.example.utils.RestAssuredConfig;
import io.cucumber.java.Before;
import org.example.context.ApiScenarioContext;

public class CommonCucumberHooks {
    public static final ThreadLocal<ApiScenarioContext> context = new ThreadLocal<>();

    @Before(order = 1)
    public void setup() {
        RestAssuredConfig.setup();
        context.set(new ApiScenarioContext());
    }
}