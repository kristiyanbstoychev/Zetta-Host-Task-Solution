package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static tests.BaseTest.getWait;
import static tests.GlobalVariables.driver;

public class SiteCrawlerLocators extends BasePage {

    //5 to 26
    @FindBy(css = "[data-menu-id]")
    private List<WebElement> shopByDepartmentLinks;

    @FindBy(css = "[data-csa-c-slot-id='HamburgerMenuDesktop']")
    private WebElement hamburgerMenuDesktop;

    @FindBy(id = "hmenu-customer-profile")
    private WebElement signUpMessage;

    @FindBy(css = "[class='hmenu hmenu-visible hmenu-translateX']")
    private WebElement subMenuList;

    @FindBy(css = "[class='hmenu-item hmenu-compressed-btn']")
    private List<WebElement> showAllItemsButton;

    @FindBy(css = "[class='hmenu-item hmenu-expanded-btn']")
    private List<WebElement> showLessItemsButton;

    @FindBy(xpath = "//ul[@class='hmenu hmenu-visible']//li")
    private List<WebElement> dropdownMenuItems;

    @FindBy(xpath = "//ul[@class='hmenu hmenu-visible hmenu-translateX']//li//a[@class='hmenu-item']")
    private List<WebElement> departmentSubMenuItems;
    @FindBy(css = "[class='nav-sprite hmenu-close-icon']")
    private WebElement closeHamburgerMenuButton;

    @FindBy(css = "[class='s-desktop-width-max s-desktop-content s-opposite-dir s-wide-grid-style sg-row']")
    private WebElement departmentPageResults;

    public int loopStart;
    public int loopEnd;

    public  List<String> departmentURLs = new ArrayList<String>();
    public  List<String> departmenURLNames = new ArrayList<String>();
    public  List<String> departmenURLStatuses = new ArrayList<String>();


    public void verifyLinks() {
        getWait().until(ExpectedConditions.elementToBeClickable(hamburgerMenuDesktop));
        hamburgerMenuDesktop.click();

        getWait().until(ExpectedConditions.visibilityOf(signUpMessage));

        showAllItemsButton.getFirst().click();

        loopStart = getLoopStartingIndex();
        loopEnd = getLoopEndIndex();

        closeHamburgerMenuButton.click();

        verifyDepartmentLinks();

    }

    public void verifyDepartmentLinks() {
        for (int i = loopStart; i < 6; i++) {
            getWait().until(ExpectedConditions.elementToBeClickable(hamburgerMenuDesktop));
            TimeUtils.waitForSeconds(1);
            hamburgerMenuDesktop.click();

            getWait().until(ExpectedConditions.visibilityOf(signUpMessage));

            getWait().until(ExpectedConditions.elementToBeClickable(shopByDepartmentLinks.get(i)));
            shopByDepartmentLinks.get(i).click();
            getWait().until(ExpectedConditions.visibilityOf(subMenuList));
            for (WebElement departmentLink : departmentSubMenuItems) {
                String url = departmentLink.getAttribute("href");
                String urlName = departmentLink.getText();
                departmentURLs.add(url);
                departmenURLNames.add(urlName);
                driver.navigate().to(url);
                try {
                    getWait().until(ExpectedConditions.visibilityOf(departmentPageResults));
                    departmenURLStatuses.add("OK");
                }
                catch (Exception e) {
                    departmenURLStatuses.add("NOT OK");
                }
                driver.navigate().back();
            }
        }
        System.out.println(departmenURLNames);
        System.out.println(departmentURLs);
        System.out.println(departmenURLStatuses);
    }
    public int getLoopStartingIndex() {
        int initialId = 0;
        getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//ul[@class='hmenu hmenu-visible']//li"), 1));

        for (int i = 1; i < (dropdownMenuItems.size() - 1); i++) {
            if (Objects.equals(dropdownMenuItems.get(i).getText(), "Shop by Department")) {
                break;
            }
            initialId++;
        }
        return initialId;
    }

    public int getLoopEndIndex() {
        int initialId = 0;
        getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//ul[@class='hmenu hmenu-visible']//li"), 1));

        for (int i = 0; i < (dropdownMenuItems.size() - 1); i++) {
            if (Objects.equals(dropdownMenuItems.get(i).getText(), "See less")) {
                break;
            }
            if (!dropdownMenuItems.get(i).findElements(By.cssSelector("[data-ref-tag]")).isEmpty()) {
                initialId++;
            }
        }
        return initialId;
    }
}
