@vehicle
Feature: In order to verify vehicle details
  As a user I want to check the vehicle information from cartaxcheck.co.uk

    Scenario:Scenario: Check the vehical information based on the registration number
      Given I read vehicle registration number from "txt,csv,xlsx" file
      When I enter registration number on the page
      Then I see vehicle information displayed correctly
