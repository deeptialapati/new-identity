package com.identity.pages;

import com.identity.pojos.Vehicle;
import com.identity.util.WebDriverHelper;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FreeVehicleCheckDetailsPage {
    private final By REGISTRATION_NUMBER_SELECTOR = By.xpath("//dt[text()='Registration']/following-sibling::dd");
    private final By MAKE_SELECTOR = By.xpath("//dt[text()='Make']/following-sibling::dd");
    private final By MODEL_SELECTOR = By.xpath("//dt[text()='Model']/following-sibling::dd");
    private final By COLOR_SELECTOR = By.xpath("//dt[text()='Colour']/following-sibling::dd");
    private final By YEAR_SELECTOR = By.xpath("//dt[text()='Year']/following-sibling::dd");
    private final By FOOTER_SITEMAP_SELECTOR = By.cssSelector("a[href='/sitemap/']");
    private final By VEHICLE_NOT_FOUND_MODEL = By.cssSelector(".modal-content a[href='/?vrm=BW57BOW']");
    @Autowired
    private WebDriverHelper webDriverHelper;

    public Vehicle getVehicleIdentityDetails() {
        webDriverHelper.waitForElementToPresent(VEHICLE_NOT_FOUND_MODEL, 2);
        if (!webDriverHelper.isElementDisplayed(VEHICLE_NOT_FOUND_MODEL)) {
            Vehicle vehicle = new Vehicle();
            webDriverHelper.waitForTextLengthGreaterThanZero(FOOTER_SITEMAP_SELECTOR, 10);
            webDriverHelper.waitForElementToPresent(YEAR_SELECTOR, 10);
            vehicle.setMake(webDriverHelper.getText(MAKE_SELECTOR));
            vehicle.setModel(webDriverHelper.getText(MODEL_SELECTOR));
            vehicle.setColour(webDriverHelper.getText(COLOR_SELECTOR));
            vehicle.setYear(webDriverHelper.getText(YEAR_SELECTOR));
            vehicle.setRegistrationNumber(webDriverHelper.getText(REGISTRATION_NUMBER_SELECTOR));
            return vehicle;
        }
        return null;
    }

}
