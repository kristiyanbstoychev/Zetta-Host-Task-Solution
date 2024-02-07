package pages;


import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

import static tests.BaseTest.getWait;
import static tests.GlobalVariables.isMobile;

public class HomePage extends BasePage {

    //Locators for the home page
    @FindBy(id = "nav-progressive-greeting")
    private WebElement signInButtonNavigationMobile;

    @FindBy(id = "nav-link-accountList")
    private List<WebElement> signInButtonNavigationDesktop;

    @FindBy(id = "nav-progressive-greeting")
    private WebElement logoNavigationMobile;

    @FindBy(id = "nav-hamburger-menu")
    private List<WebElement> hamburgerMenu;

    @FindBy(id = "nav-button-cart")
    private WebElement cartButtonMobile;

    @FindBy(id = "nav-cart")
    private WebElement cartButtonDesktop;

    @FindBy(id = "gw-sign-in-button")
    private WebElement signInButtonMobile;

    @FindBy(id = "nav-gwbar")
    private WebElement navigationBarMobile;

    @FindBy(id = "nav-xshop")
    private WebElement navigationBarDesktop;

    @FindBy(id = "glow-ingress-single-line")
    private WebElement deliveryLocationDropdownMobile;

    @FindBy(id = "nav-global-location-popover-link")
    private WebElement deliveryLocationDropdownDesktop;

    @FindBy(id = "nav-ftr")
    private WebElement footerMobile;

    @FindBy(id = "navFooter")
    private WebElement footerDesktop;

    //Test methods
    //Verifies that some of the elements on the home page are visible(clickable)
    public void verifyHomePageContent() {
        if (isMobile) {
            getWait().until(ExpectedConditions.elementToBeClickable(searchInputFieldMobile));
            getWait().until(ExpectedConditions.elementToBeClickable(submitSearchButtonMobile));
            getWait().until(ExpectedConditions.elementToBeClickable(signInButtonNavigationMobile));
            getWait().until(ExpectedConditions.elementToBeClickable(logoNavigationMobile));
            getWait().until(ExpectedConditions.elementToBeClickable(hamburgerMenu.getFirst()));
            getWait().until(ExpectedConditions.elementToBeClickable(cartButtonMobile));
            getWait().until(ExpectedConditions.elementToBeClickable(signInButtonMobile));
            getWait().until(ExpectedConditions.elementToBeClickable(navigationBarMobile));
            getWait().until(ExpectedConditions.elementToBeClickable(footerMobile));
        }
        else {
            getWait().until(ExpectedConditions.visibilityOf(searchInputFieldDesktop));
            getWait().until(ExpectedConditions.elementToBeClickable(submitSearchButtonDesktop));
            getWait().until(ExpectedConditions.elementToBeClickable(signInButtonNavigationDesktop.getFirst()));
            getWait().until(ExpectedConditions.elementToBeClickable(hamburgerMenu.getLast()));
            getWait().until(ExpectedConditions.elementToBeClickable(cartButtonDesktop));
            getWait().until(ExpectedConditions.elementToBeClickable(signInButtonNavigationDesktop.getFirst()));
            getWait().until(ExpectedConditions.elementToBeClickable(navigationBarDesktop));
            getWait().until(ExpectedConditions.elementToBeClickable(footerDesktop));
        }

    }
}
