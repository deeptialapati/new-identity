Getting Started
===============
Pre-requisites:
* Install minimum JDK 1.8 and set up with environment variable
* Set up Maven 4 and set up with environment variable
* Download latest chrome driver to below path ${project.base.dir}\src\test\resources\config\drivers

How Run Tests
=============
Tests can be run with Maven or with Intellij

1.Run tests with maven
---------------
Go to project directory where pom.xml located, open command prompt
To execute using maven.

 ```
 mvn clean integration-test -Dcucumber.filter.tags=@vehicle
 ```

2.Run tests with intellij
---------------
The IntelliJ Cucumber Java runner works better than its JUnit runner for running Gherkin features.
You can jump between the test runner pane and the Gherkin definitions & can run individual scenarios
instead of the entire feature file.

To set this up, open the Edit Run Configurations dialog.

Add the following to the defaults for Cucumber Java tests:

Main Class: io.cucumber.core.cli.Main
Glue: com.identity

Now you can run feature files from the right-button popup menu.

### Notes ###
* I've run these tests on Mac for Chrome
* One assertion failed because of registration number BW57BOF is not found(taken from car_input.txt) 

### Framework ###
I've implemented the framework using cucumber-JVM integrated with Spring using Maven and a page Object Model.

