package tests;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.PageFactory;
import pages.SearchLocators;

import static tests.GlobalVariables.driver;

public class SearchTests extends BaseTest {

    @Test
    void searchByText() {
        SearchLocators searchLocators = PageFactory.initElements(driver, SearchLocators.class);
        searchLocators.addOnlyDiscountedProductsToCart();
    }
}
