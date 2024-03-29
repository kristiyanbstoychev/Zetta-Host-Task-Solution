package tests;
import org.openqa.selenium.WebDriver;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class GlobalVariables {

    //---------Test setup variables---------//
    //variables storing the urls for testing
    public final static String baseURL = "https://www.amazon.com/";

    public final static String currentWorkingDirectory = Path.of("").toAbsolutePath().toString();

    public static WebDriver driver;

    //default browser and device, used in cases where no environment variables are defined
    public static final String defaultBrowser = "chrome";
    public static final String defaultDevice = "iPhone XR";
    //Samsung Galaxy S20 Ultra
    //Galaxy S9+
    //iPhone XR
    //iPhone 6/7/8

    //environment variable names
    public static final String urlForTestingEnvVariable = "URL_FOR_TESTING";
    public static final String browserForTestingEnvVariableName = "BROWSER_FOR_TESTING";
    public static final String deviceForTestingEnvVariableName = "DEVICE_FOR_TESTING";

    //saves the pre-defined environment variable to a string
    public static String getEnvVariable(String environmentVariable, String defaultValue) {
        String envVariable = System.getenv(environmentVariable);
        if (envVariable == null || isDebugModeEnabled) {
            envVariable = defaultValue;
        }
        return envVariable;
    }

    //Boolean used to trigger the mobile version of the tests
    public static boolean isMobile = false;

    //boolean which checks whether tests are executed in debug mode or not
    public static final boolean isDebugModeEnabled = java.lang.management.ManagementFactory.getRuntimeMXBean().getInputArguments().toString().contains("jdwp");

}
