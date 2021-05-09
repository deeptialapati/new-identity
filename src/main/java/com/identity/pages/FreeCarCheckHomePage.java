package com.identity.pages;

import com.identity.util.WebDriverHelper;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FreeCarCheckHomePage {
    private final By REGISTRATION_TEXT_BOX_SELECTOR = By.id("vrm-input");
    private final By FREE_CAR_CHECK_BUTTON_SELECTOR = By.cssSelector("form[action='/free-car-check/'] button");
    @Autowired
    private WebDriverHelper webDriverHelper;

    public void navigateToPage(String url) {
        webDriverHelper.navigateToURL(url);
    }

    public void enterRegistrationNumber(String registrationNumber) {
        webDriverHelper.waitForElementToPresent(REGISTRATION_TEXT_BOX_SELECTOR, 5);
        webDriverHelper.setTextInTextbox(REGISTRATION_TEXT_BOX_SELECTOR, registrationNumber);
    }

    public void submitRegistrationNumber() {
        webDriverHelper.clickOnElement(FREE_CAR_CHECK_BUTTON_SELECTOR);
    }
}
