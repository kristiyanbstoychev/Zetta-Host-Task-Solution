package pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

import static tests.BaseTest.getWait;
import static tests.BaseTest.scrollIntoView;
import static tests.GlobalVariables.driver;
import static tests.GlobalVariables.isMobile;

public class SearchLocators extends BasePage {
    @FindBy(id = "s-refinements")
    private WebElement filterElementsDesktop;

    @FindBy(id = "s-all-filters")
    private WebElement filterElementsMobile;

    @FindBy(xpath = "//span[@data-component-type='s-search-results']")
    private WebElement searchResultsMobile;

    @FindBy(xpath = "//div[@data-csa-c-item-id]//span[@data-a-strike='true']")
    private List<WebElement> searchResultsItems;

    @FindBy(id = "nav-cart-count")
    private WebElement numberOfItemsInCart;

    @FindBy(id = "add-to-cart-button")
    private WebElement addToCartButton;

    @FindBy(xpath = "//span[contains(text(), 'Added to Cart')]")
    private WebElement addedToCartSuccessMessage;

    public void globalSearchByText(String searchText) {
        if (isMobile) {
            searchInputFieldMobile.sendKeys(searchText);
            submitSearchButtonMobile.click();

            getWait().until(ExpectedConditions.visibilityOf(searchResultsMobile));
        }
    }

    public void addOnlyDiscountedProductsToCart() {
        globalSearchByText("laptop");

        String initialCartItemsCount = numberOfItemsInCart.getText();
        String newCartItemsCount;

        List<WebElement> elements = searchResultsItems;
        for (WebElement element : elements) {
            scrollIntoView(element);
            getWait().until(ExpectedConditions.elementToBeClickable(element));
            element.click();

            getWait().until(ExpectedConditions.elementToBeClickable(addToCartButton));
            System.out.println("Product page displayed");

            addToCartButton.click();
            System.out.println("Add to cart button clicked");

            getWait().until(ExpectedConditions.visibilityOf(addedToCartSuccessMessage));
            newCartItemsCount = numberOfItemsInCart.getText();
            Assertions.assertNotSame(initialCartItemsCount, newCartItemsCount);

            getWait().until(ExpectedConditions.textToBePresentInElement(numberOfItemsInCart, newCartItemsCount));

            driver.navigate().back();
            getWait().until(ExpectedConditions.elementToBeClickable(addToCartButton));
            driver.navigate().back();

            System.out.println("Product Added to Cart");
        }
    }

}
