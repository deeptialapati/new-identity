package com.identity;

import com.identity.pages.FreeCarCheckHomePage;
import com.identity.pages.FreeVehicleCheckDetailsPage;
import com.identity.pojos.FileDetails;
import com.identity.pojos.Vehicle;
import com.identity.services.FileDetailsService;
import com.identity.services.VehicleDataService;
import com.identity.util.Utils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.SoftAssertions;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;

@ContextConfiguration("classpath:cucumber.xml")
public class VehicleInformationSteps {
    @Autowired
    Utils utils;
    Map<String, List<FileDetails>> fileDetailsListMap;
    List<Vehicle> expectedVehicleList;
    List<Vehicle> actualVehicleList = new ArrayList<>();
    @Autowired
    private VehicleDataService vehicleDataService;
    @Autowired
    private FileDetailsService fileDetailsService;
    @Autowired
    private FreeCarCheckHomePage freeCarCheckHomePage;
    @Autowired
    private FreeVehicleCheckDetailsPage freeVehicleCheckDetailsPage;
    @Value("${test.url}")
    private String testUrl;

    @Given("I read vehicle registration number from {string} file")
    public void iReadVehicleRegistratioNumberFromFile(String fileFormat) throws URISyntaxException {
        File file = getFilePathWitResource("testdata/input");
        fileDetailsListMap = fileDetailsService.getFileDetailsFromADirector(file.getAbsolutePath());
        expectedVehicleList = vehicleDataService.readFile(Arrays.asList(fileFormat.split(",")), fileDetailsListMap);
    }


    @When("I enter registration number on the page")
    public void iEnterRegistrationNumberOnThePage() {
        expectedVehicleList.forEach(v -> {
            freeCarCheckHomePage.navigateToPage(testUrl);
            freeCarCheckHomePage.enterRegistrationNumber(v.getRegistrationNumber());
            freeCarCheckHomePage.submitRegistrationNumber();
            actualVehicleList.add(freeVehicleCheckDetailsPage.getVehicleIdentityDetails());
        });

    }

    @Then("^I see vehicle information displayed correctly$")
    public void iSeeVehicleInformationDisplayedCorrectly() throws Throwable {
        File file = getFilePathWitResource("testdata/output/car_output.txt");
        List<Vehicle> expectedVehiclesList = vehicleDataService.convertTextFileToVehicleObject(file.getAbsolutePath());
        SoftAssertions softAssertions = new SoftAssertions();
        expectedVehiclesList.forEach(expectedVehicle -> {
            Vehicle actualVehicleDetails = actualVehicleList
                    .stream()
                    .filter(Objects::nonNull)
                    .filter(actualVehicle -> actualVehicle.getRegistrationNumber().equalsIgnoreCase(expectedVehicle.getRegistrationNumber()))
                    .findFirst().orElse(null);
            if (actualVehicleDetails != null) {
                softAssertions.assertThat(actualVehicleDetails.getColour()).isEqualTo(expectedVehicle.getColour());
                softAssertions.assertThat(actualVehicleDetails.getMake()).isEqualTo(expectedVehicle.getMake());
                softAssertions.assertThat(actualVehicleDetails.getModel()).isEqualTo(expectedVehicle.getModel());
                softAssertions.assertThat(actualVehicleDetails.getYear()).isEqualTo(expectedVehicle.getYear());
            } else {
                softAssertions.fail(expectedVehicle.getRegistrationNumber() + " Registration no not found");
            }

        });
        softAssertions.assertAll();

    }

    @NotNull
    private File getFilePathWitResource(String testData) throws URISyntaxException {
        URL resource = FreeVehicleCheckDetailsPage.class.getClassLoader().getResource(testData);
        File file = Paths.get(resource.toURI()).toFile();
        return file;
    }
}
