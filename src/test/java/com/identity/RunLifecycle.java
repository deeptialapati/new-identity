package com.identity;

import com.identity.context.Context;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@CucumberContextConfiguration
@ContextConfiguration("classpath:cucumber.xml")
public class RunLifecycle {

    @Autowired
    private Context context;

    @Before
    public void beforeScenario(Scenario scenario) {
        context.setUp(scenario);
    }

    @After
    public void afterScenario(Scenario scenario) {
        context.afterScenarioFailedReport(scenario);
        context.tearDown();
    }


}
