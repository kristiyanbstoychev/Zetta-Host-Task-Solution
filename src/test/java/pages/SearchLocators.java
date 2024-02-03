package pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
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

    //span[@data-a-strike='true']
    @FindBy(xpath = "//div[@data-csa-c-item-id]//span[@data-component-type='s-pinch-to-zoom']")
    private List<WebElement> searchResultsItems;

    @FindBy(xpath = "//span[@class='a-size-double-large a-color-price savingPriceOverride aok-align-center reinventPriceSavingsPercentageMargin savingsPercentage']")
    private WebElement productPageProductDiscountPercentage;

    @FindBy(id = "nav-cart-count")
    private WebElement numberOfItemsInCart;

    @FindBy(id = "add-to-cart-button")
    private WebElement addToCartButton;

    @FindBy(id = "title")
    private WebElement productNameLocator;

    @FindBy(xpath = "//div//img[@src='']")
    private WebElement productImageLocator;

    @FindBy(xpath = "//span[@class='a-price aok-align-center reinventPricePriceToPayMargin priceToPay']")
    private WebElement productPriceLocator;

    @FindBy(xpath = "//a[contains(text(), 'Go to Cart')]")
    private WebElement goToCartButton;

    @FindBy(xpath = "//span[contains(text(), 'Added to Cart')]")
    private WebElement addedToCartSuccessMessage;
    @FindBy(name = "proceedToRetailCheckout")
    private WebElement proceedToRetailCheckoutButton;

    public static String searchURL;
    public  String productName;
    public String productPrice;
    public static String productImage;

    public void globalSearchByText(String searchText) {
        if (isMobile) {
            searchInputFieldMobile.sendKeys(searchText);
            submitSearchButtonMobile.click();

            getWait().until(ExpectedConditions.visibilityOf(searchResultsMobile));
            searchURL = driver.getCurrentUrl();
        }
    }

    public void addOnlyNonDiscountedProductsToCart() {
        globalSearchByText("laptop");

        addNonDiscountedElementToCart();
    }

    public void addNonDiscountedElementToCart() {

        for (int i = 0; i < (searchResultsItems.size() - 1); i++) {
            WebElement elementToUse = searchResultsItems.get(i);

            getWait().until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@data-csa-c-item-id]")));
            getWait().until(ExpectedConditions.elementToBeClickable(elementToUse));
            scrollIntoView(elementToUse);
            elementToUse.click();

            try {
                getWait().until(ExpectedConditions.visibilityOf(productPageProductDiscountPercentage));
                getWait().until(ExpectedConditions.visibilityOf(addToCartButton));
                System.out.println("product is at a discounted price");
                driver.navigate().to(searchURL);
            }
            catch (Exception e) {
                addProductToCart();

                verifyCartContent();

                driver.navigate().to(searchURL);
            }
        }
    }

    public void addProductToCart() {
        String initialCartItemsCount = numberOfItemsInCart.getText();
        String newCartItemsCount;

        productName = productNameLocator.getText();
        productPrice = productPriceLocator.getText();
        productImage = driver.findElement(By.xpath("//div//img[@alt='"+ productName +"']")).getAttribute("src");

        addToCartButton.click();
        System.out.println("Add to cart button clicked");

        getWait().until(ExpectedConditions.visibilityOf(addedToCartSuccessMessage));
        newCartItemsCount = numberOfItemsInCart.getText();
        Assertions.assertNotSame(initialCartItemsCount, newCartItemsCount);

        getWait().until(ExpectedConditions.textToBePresentInElement(numberOfItemsInCart, newCartItemsCount));
    }

    public void verifyCartContent() {
        getWait().until(ExpectedConditions.elementToBeClickable(goToCartButton));
        goToCartButton.click();
        getWait().until(ExpectedConditions.visibilityOf(proceedToRetailCheckoutButton));

        String bodyText = driver.findElement(By.tagName("body")).getText();

        Assertions.assertTrue(bodyText.contains(productName));
        Assertions.assertTrue(bodyText.contains(productPrice));
        System.out.println("cart content verified");
    }

}
