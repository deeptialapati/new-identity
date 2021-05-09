package com.identity.browser;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

public class BrowserDriver extends EventFiringWebDriver {
    public BrowserDriver(WebDriver driver) {
        super(driver);
    }
}
