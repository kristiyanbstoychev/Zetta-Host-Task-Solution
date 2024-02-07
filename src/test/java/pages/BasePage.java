package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class BasePage {

    //Common locators for all tests
    @FindBy(id = "twotabsearchtextbox")
    public WebElement searchInputFieldDesktop;

    @FindBy(id = "nav-search-keywords")
    public WebElement searchInputFieldMobile;

    @FindBy(css = "[class='nav-search-submit nav-bluebeacon']")
    public WebElement submitSearchButtonMobile;

    @FindBy(id = "nav-search-submit-button")
    public WebElement submitSearchButtonDesktop;
}
