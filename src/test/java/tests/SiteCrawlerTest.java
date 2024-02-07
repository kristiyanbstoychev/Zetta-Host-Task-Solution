package tests;


import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.PageFactory;
import pages.SiteCrawlerLocators;

import java.io.IOException;

import static tests.GlobalVariables.driver;

public class SiteCrawlerTest extends BaseTest {

    @Test
    void verifyShopByDepartmentLinks() throws IOException {
        SiteCrawlerLocators siteCrawlerLocators = PageFactory.initElements(driver, SiteCrawlerLocators.class);
        siteCrawlerLocators.extractAndVerifyUrlStatuses();
    }
}
