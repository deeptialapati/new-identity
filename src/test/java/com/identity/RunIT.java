package com.identity;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * Triggers all Cucumber tests in this package.
 * Both .feature and .java/.class files must be in the same package!
 * <p/>
 * Note: maven-failsage-reporting plugin requires this class to be suffixed with "IT".
 */
@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty", "html:target/cucumber/index.html"})
public class RunIT {
}
