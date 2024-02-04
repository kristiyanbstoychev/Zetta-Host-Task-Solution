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
    private WebElement searchResults;

    //span[@data-a-strike='true']
    @FindBy(xpath = "//div[@data-csa-c-item-id]//span[@data-component-type='s-pinch-to-zoom']")
    private List<WebElement> searchResultsItemsMobile;

    @FindBy(xpath = "//div[@data-csa-c-item-id]//span[@data-component-type='s-product-image']")
    private List<WebElement> searchResultsItemsDesktop;

    @FindBy(xpath = "//span[@class='a-size-double-large a-color-price savingPriceOverride aok-align-center reinventPriceSavingsPercentageMargin savingsPercentage']")
    private WebElement productPageProductDiscountPercentageMobile;

    @FindBy(xpath = "//span[@class='a-size-large a-color-price savingPriceOverride aok-align-center reinventPriceSavingsPercentageMargin savingsPercentage']")
    private WebElement productPageProductDiscountPercentageDesktop;

    @FindBy(id = "nav-cart-count")
    private WebElement numberOfItemsInCart;

    @FindBy(id = "add-to-cart-button")
    private WebElement addToCartButtonMobile;

    @FindBy(id = "submit.add-to-cart")
    private List<WebElement> addToCartButtonDesktop;

    @FindBy(id = "productTitle")
    private WebElement productNameLocatorDesktop;

    @FindBy(id = "title")
    private WebElement productNameLocatorMobile;

    @FindBy(xpath = "//div//img[@src='']")
    private WebElement productImageLocator;

    @FindBy(xpath = "//span[@class='a-price aok-align-center reinventPricePriceToPayMargin priceToPay']")
    private WebElement productPriceLocator;

    @FindBy(xpath = "//a[contains(text(), 'Go to Cart')]")
    private List<WebElement> goToCartButton;

    @FindBy(xpath = "//span[contains(text(), 'Added to Cart')]")
    private WebElement addedToCartSuccessMessage;
    @FindBy(name = "proceedToRetailCheckout")
    private WebElement proceedToRetailCheckoutButton;

    @FindBy(className = "a-truncate-cut")
    private List<WebElement> checkOutProductName;

    @FindBy(className = "a-price-whole")
    private List<WebElement> checkOutProductPriceMobile;

    @FindBy(className = "sc-badge-price-to-pay")
    private WebElement checkOutProductPriceDesktop;

    public static String searchURL;
    public  String productName;
    public String productPrice;
    public static String productImage;

    public void globalSearchByText(String searchText) {
        if (isMobile) {
            searchInputFieldMobile.sendKeys(searchText);
            submitSearchButtonMobile.click();

        }
        else {
            searchInputFieldDesktop.sendKeys(searchText);
            submitSearchButtonDesktop.click();

        }
        getWait().until(ExpectedConditions.visibilityOf(searchResults));
        searchURL = driver.getCurrentUrl();
    }

    public void addOnlyNonDiscountedProductsToCart() {
        globalSearchByText("laptop");

        addNonDiscountedElementToCart();
    }

    public void addNonDiscountedElementToCart() {
        List<WebElement> searchResults;
        WebElement priceDiscountElement;

        if (isMobile) {
            searchResults = searchResultsItemsMobile;
            priceDiscountElement = productPageProductDiscountPercentageMobile;
        }
        else {
            searchResults = searchResultsItemsDesktop;
            priceDiscountElement = productPageProductDiscountPercentageDesktop;
        }

        for (int i = 0; i < (searchResults.size()- 1); i++) {
            WebElement elementToUse = searchResults.get(i);

            getWait().until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@data-csa-c-item-id]")));
            getWait().until(ExpectedConditions.elementToBeClickable(elementToUse));
            scrollIntoView(elementToUse);
            elementToUse.click();

            try {
                getWait().until(ExpectedConditions.visibilityOf(priceDiscountElement));
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
        WebElement addToCartButton;
        WebElement productNameLocator;

        if (isMobile) {
            addToCartButton = addToCartButtonMobile;
            productNameLocator = productNameLocatorMobile;
        }
        else {
            addToCartButton = addToCartButtonDesktop.getFirst();
            productNameLocator = productNameLocatorDesktop;
        }

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
        WebElement navigateToCartButton;
        WebElement productPriceLocator;

        if (isMobile) {
            navigateToCartButton = goToCartButton.getFirst();
            productPriceLocator = checkOutProductPriceMobile.getLast();
        }
        else {
            navigateToCartButton = goToCartButton.getLast();
            productPriceLocator = checkOutProductPriceDesktop;
        }

        getWait().until(ExpectedConditions.elementToBeClickable(navigateToCartButton));
        navigateToCartButton.click();
        getWait().until(ExpectedConditions.visibilityOf(proceedToRetailCheckoutButton));

        getWait().until(ExpectedConditions.textToBePresentInElement(checkOutProductName.getFirst(), productName.substring(0, 50)));

        getWait().until(ExpectedConditions.textToBePresentInElement(productPriceLocator, productPrice.substring(0, 4)));
        System.out.println("cart content verified");
    }

}
