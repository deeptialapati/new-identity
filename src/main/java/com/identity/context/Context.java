package com.identity.context;

import com.identity.browser.BrowserDriver;
import com.identity.browser.BrowserFactory;
import com.identity.util.WebDriverHelper;
import io.cucumber.java.Scenario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Context {
    private Scenario scenario;
    @Autowired
    private BrowserFactory browserFactory;

    private BrowserDriver browserDriver;


    @Autowired
    private WebDriverHelper webDriverHelper;

    public void setUp(Scenario scenario) {
        System.out.println("Context.setup() called with scenario: " + scenario);
        this.scenario = scenario;
        this.browserDriver = browserFactory.create();
    }

    public void tearDown() {
        this.browserDriver.quit();
    }

    public BrowserDriver getBrowser() {
        return this.browserDriver;
    }

    public Scenario getScenario() {
        return this.scenario;
    }


    public void afterScenarioFailedReport(Scenario scenario) {
        if (scenario.isFailed()) {
            webDriverHelper.screenshot();
        }
    }
}
