# Zetta-Host-Task-Solution

A project based on Selenium Web river and Java providing a solution to the task by Zetta Host.


The project is based on the Page object model. Tests and locators are created in separate classes.
The PAGES package contains the locators and methods needed for testing a certain page. 

## Tests setup:
Before the start of each test, the pickBrowser method is called. The method is responsible for determining the browser and device used for testing.
For the tests to be easily implemented with a CI/CD environment a set of environment variables are used (BROWSER_FOR_TESTING and DEVICE_FOR_TESTING).
Default values are set for both variables. The URL for testing is also stored in an env variable (URL_FOR_TESTING).
After the browser and device for testing are determined a set of certain browser capabilities are applied.
By default, tests are run in headless mode and in incognito mode, with all cookies deleted. You can run them in a non-headless browser by executing the in debug mode.

Tests can be executed both for mobile and desktop devices (excluding the SiteCrawler tests), by changing the value of defaultBrowser to chrome, firefox or mobile respectively.
Tests can also be executed against a specific mobile device by changing the value of the defaultDevice variable to any device listed in the DevTools device simulation window.

The setup of the browser viewport, default device and URL can also be passed from the command line before executing the tests themself.

``
export URL_FOR_TESTING="url for testing"
``

``
export BROWSER_FOR_TESTING="browser"
``

``
export DEVICE_FOR_TESTING="device"
``

``
./gradlew -i clean test --tests 'TestSuiteRunnerCFUI'
``


## Test Execution
There are two ways of executing the test:

### Via command line:

``
./gradlew -i clean test --tests 'TestSuiteRunner'
``

### Running the TestSuiteRunner class
Navigate to the TestSuiteRunner class and click run to execute all of the availabe tests