package tests;


import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.PageFactory;
import pages.SiteCrawlerLocators;

import static tests.GlobalVariables.driver;

public class SiteCrawlerTest extends BaseTest {

    @Test
    void test() {
        SiteCrawlerLocators siteCrawlerLocators = PageFactory.initElements(driver, SiteCrawlerLocators.class);
        siteCrawlerLocators.verifyLinks();
    }
}
