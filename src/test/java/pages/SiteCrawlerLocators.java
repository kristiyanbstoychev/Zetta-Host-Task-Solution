package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.TimeUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static tests.BaseTest.getWait;
import static tests.GlobalVariables.baseURL;
import static tests.GlobalVariables.driver;
import static utils.CSVHandler.saveListDataToTxtFile;

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

    public  List<String> results = new ArrayList<String>();

    public String responseStatus = null;

    public void exctractAndVerifyUrlStatuses() throws IOException {
        getWait().until(ExpectedConditions.elementToBeClickable(hamburgerMenuDesktop));
        hamburgerMenuDesktop.click();

        getWait().until(ExpectedConditions.visibilityOf(signUpMessage));

        showAllItemsButton.getFirst().click();

        loopStart = getLoopStartingIndex();
        loopEnd = getLoopEndIndex();

        closeHamburgerMenuButton.click();

        verifyDepartmentLinks();
    }

    public void verifyDepartmentLinks() throws IOException {
        for (int i = loopStart; i < loopEnd; i++) {
            getWait().until(ExpectedConditions.elementToBeClickable(hamburgerMenuDesktop));
            hamburgerMenuDesktop.click();

            getWait().until(ExpectedConditions.visibilityOf(signUpMessage));

            if (showAllItemsButton.getFirst().isDisplayed()) {
                getWait().until(ExpectedConditions.elementToBeClickable(showAllItemsButton.getFirst()));
                showAllItemsButton.getFirst().click();
            }

            getWait().until(ExpectedConditions.elementToBeClickable(shopByDepartmentLinks.get(i)));
            TimeUtils.waitForSeconds(1);
            System.out.println(shopByDepartmentLinks.get(i).getText());
            shopByDepartmentLinks.get(i).click();
            getWait().until(ExpectedConditions.visibilityOf(subMenuList));

            for (int j = 0; j < (departmentSubMenuItems.size() - 1); j++) {
                getWait().until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//ul[@class='hmenu hmenu-visible hmenu-translateX']//li//a[@class='hmenu-item']")));
                TimeUtils.waitForSeconds(1);
                String url = departmentSubMenuItems.get(j).getAttribute("href");
                String urlName = departmentSubMenuItems.get(j).getText();
                results.add(urlName);
                results.add(url);
                veriftLinkStatusCode(url);
                results.add(responseStatus);
            }
            driver.navigate().to(baseURL);
        }
        saveListDataToTxtFile(results);
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

    public void veriftLinkStatusCode(String url) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection httpURLConnection = (HttpURLConnection) obj.openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setRequestProperty("User-Agent", "Chrome/121.0.0.0");
        int responseCode = httpURLConnection.getResponseCode();

        if (responseCode == 200) {
            responseStatus = "OK";
        }
        else {
            responseStatus = "Dead Link";
        }
    }

}
