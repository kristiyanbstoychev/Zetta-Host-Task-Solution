package tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.safari.SafariOptions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.BasePage;
import pages.HomePage;
import utils.TimeUtils;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import utils.CSVHandler;

import static tests.GlobalVariables.browserForTestingEnvVariableName;
import static tests.GlobalVariables.baseURL;
import static tests.GlobalVariables.csvEmailsList;
import static tests.GlobalVariables.csvNicknamesList;
import static tests.GlobalVariables.defaultBrowser;
import static tests.GlobalVariables.defaultDevice;
import static tests.GlobalVariables.deviceForTestingEnvVariableName;
import static tests.GlobalVariables.driver;
import static tests.GlobalVariables.getEnvVariable;
import static tests.GlobalVariables.isDebugModeEnabled;
import static tests.GlobalVariables.isMobile;
import static tests.GlobalVariables.urlForTestingEnvVariable;

//Execute tests in alphabetical order
@TestMethodOrder(MethodOrderer.MethodName.class)

public class BaseTest {

    //Executed before each test
    @BeforeEach
    public void doBeforeEachTest() {
        BasePage basePage = PageFactory.initElements(driver, BasePage.class);
        pickBrowser(getEnvVariable(browserForTestingEnvVariableName, defaultBrowser));

        if (getEnvVariable(browserForTestingEnvVariableName, defaultBrowser).equals("mobile")) {
            isMobile = true;
        }

        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.navigate().to(getEnvVariable(urlForTestingEnvVariable, baseURL));

        try {
            getWait().until(ExpectedConditions.titleIs("Amazon.com. Spend less. Smile more."));
        }
        catch (Exception e) {
            getWait().until(ExpectedConditions.visibilityOf(driver.findElement(By.id("captchacharacters"))));
            driver.navigate().refresh();
            getWait().until(ExpectedConditions.titleIs("Amazon.com. Spend less. Smile more."));
        }
    }

    //Picks which browser and browser options should be used for testing
    public static void pickBrowser(String browser) {
        switch (browser) {
            case "chrome": {
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--incognito");
                chromeOptions.addArguments("--ignore-certificate-errors");
                //if tests are executed in debug mode, start the browser in a non-headless window
                if (!isDebugModeEnabled) {
                    chromeOptions.addArguments("--headless=new", "--window-size=1920,1200");
                }
                chromeOptions.addArguments("--allow-running-insecure-content");
                chromeOptions.addArguments("--lang=en_US");
                chromeOptions.addArguments("--disable-gpu");
                chromeOptions.addArguments("-disable-features=NetworkService");
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--remote-allow-origins=*");
                chromeOptions.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
                driver = new ChromeDriver(chromeOptions);
                break;
            }
            case "firefox": {
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("--incognito");
                if (!isDebugModeEnabled) {
                    firefoxOptions.addArguments("--headless=new", "--window-size=1920,1200");
                }
                firefoxOptions.addArguments("--lang=en_US");
                firefoxOptions.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
                driver = new FirefoxDriver(firefoxOptions);
                break;
            }
            case "safari": {
                SafariOptions safariOptions = new SafariOptions();
                break;
            }
            case "mobile": {
                Map<String, String> mobileEmulation = new HashMap<>();
                mobileEmulation.put("deviceName", getEnvVariable(deviceForTestingEnvVariableName, defaultDevice));
                ChromeOptions mobileOptions = new ChromeOptions();
                mobileOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
                mobileOptions.addArguments("--incognito");
                mobileOptions.addArguments("--ignore-certificate-errors");
                if (!isDebugModeEnabled) {
                    mobileOptions.addArguments("--headless=new");
                }
                mobileOptions.addArguments("--allow-running-insecure-content");
                mobileOptions.addArguments("--lang=en_US");
                mobileOptions.addArguments("--disable-gpu");
                mobileOptions.addArguments("-disable-features=NetworkService");
                mobileOptions.addArguments("--no-sandbox");
                mobileOptions.addArguments("--remote-allow-origins=*");
                mobileOptions.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
                driver = new ChromeDriver(mobileOptions);
                break;
            }
        }
    }


    @AfterEach
    public void closeBrowser() {
        driver.quit();
    }

    //Scrolls the page to a desired element
    public static void scrollIntoView(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", element);
        TimeUtils.waitForSeconds(3);
    }

    public static WebDriverWait getWait(long seconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(seconds));
    }

    public static WebDriverWait getWait() {
        return getWait(15);
    }
}
