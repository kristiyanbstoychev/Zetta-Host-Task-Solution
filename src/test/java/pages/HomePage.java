package pages;


import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static tests.BaseTest.getWait;
import static tests.GlobalVariables.isMobile;

public class HomePage extends BasePage {

    //Locators
    @FindBy(id = "twotabsearchtextbox")
    private WebElement searchInputFieldDesktop;

    @FindBy(id = "nav-search-keywords")
    private WebElement searchInputFieldMobile;

    @FindBy(id = "billboard-card_mobile-gateway-exports-atf_0")
    private WebElement headerSectionMobile;

    @FindBy(id = "nav-progressive-greeting")
    private WebElement signInButtonNavigationMobile;

    @FindBy(id = "nav-progressive-greeting")
    private WebElement logoNavigationMobile;

    @FindBy(id = "nav-hamburger-menu")
    private WebElement hamburgerMenuMobile;

    @FindBy(id = "nav-button-cart")
    private WebElement cartButtonMobile;

    @FindBy(id = "gw-sign-in-button")
    private WebElement signInButtonMobile;

    public WebElement getSearchInputFieldDesktop() {
        return searchInputFieldDesktop;
    }

    //Test methods
    public void verifyHomePageContent() {
        if (isMobile) {
            getWait().until(ExpectedConditions.visibilityOf(searchInputFieldMobile));
        }
        else {
            getWait().until(ExpectedConditions.visibilityOf(searchInputFieldDesktop));
        }

    }
}
