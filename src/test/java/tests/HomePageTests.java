package tests;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.PageFactory;
import pages.HomePage;

import static tests.GlobalVariables.driver;


public class HomePageTests extends BaseTest {

    @Test
    void verifyHomePageContent() {
        HomePage homePage = PageFactory.initElements(driver, HomePage.class);
        homePage.verifyHomePageContent();
    }
}
