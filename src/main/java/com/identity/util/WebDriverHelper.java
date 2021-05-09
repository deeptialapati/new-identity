package com.identity.util;

import com.identity.browser.BrowserDriver;
import com.identity.context.Context;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class WebDriverHelper {
    @Autowired
    private Context context;


    public void navigateToURL(final String url) {
        context.getBrowser().get(url);
    }


    public void clickOnElement(final By locator) {
        context.getBrowser().findElement(locator).click();
    }


    public void setTextInTextbox(final By locator, final String text) {
        context.getBrowser().findElement(locator).clear();
        context.getBrowser().findElement(locator).sendKeys(text);
    }

    public String getText(final By locator) {
        return context.getBrowser().findElement(locator).getText();
    }

    public void waitForElementToPresent(final By element, final int waitTimeInSeconds) {
        try {
            (new WebDriverWait(context.getBrowser(), Duration.ofSeconds(waitTimeInSeconds)))
                    .until(ExpectedConditions.presenceOfElementLocated(element));
        } catch (TimeoutException | NoSuchElementException exception) {
//            if catches time out exception do nothing
        }
    }

    public boolean isElementDisplayed(final By element) {
        boolean isElementDisplayed = false;
        try {
            isElementDisplayed = context.getBrowser().findElement(element).isDisplayed();
        } catch (NoSuchElementException te) {
//            if catches time out exception do nothing
        }
        return isElementDisplayed;
    }

    public void waitForTextLengthGreaterThanZero(final By element, final int waitTimeInSeconds) {
        try {
            (new WebDriverWait(context.getBrowser(), Duration.ofSeconds(waitTimeInSeconds))).until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver d) {
                    return d.findElement(element).getText().length() != 0;
                }
            });
            ;
        } catch (TimeoutException te) {
//            if catches time out exception do nothing
        }
    }

    public void screenshot() {
        BrowserDriver browser = context.getBrowser();
        if (browser != null) {
            byte[] screenshot = browser.getScreenshotAs(OutputType.BYTES);
            context.getScenario().attach(screenshot, "image/png", "ScreenShot");
        }
    }

}
