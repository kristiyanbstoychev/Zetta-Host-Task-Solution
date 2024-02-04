package tests;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;

public class GlobalVariables {

    //---------Test setup variables---------//
    //variables storing the urls for testing
    public final static String baseURL = "https://www.amazon.com/";

    //variable storing the currently used operating system
    public static String operationSystem = System.getProperty("os.name");

    //the directory, where screenshots of failed tests are stored
    public final static String localTempDirectoryWindows = System.getProperty("java.io.tmpdir") + "01_failedTestsScreenshots\\";
    public final static String localTempDirectoryLinux = System.getProperty("java.io.tmpdir") + "/01_failedTestsScreenshots/";

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

    //lists with emails and nicknames that could be used for testing, stored in csv files
    public static List<String> csvEmailsList = new ArrayList<>();
    public static List<String> csvNicknamesList = new ArrayList<>();

    public static String exampleGlobalVariable = "";
}
