package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class BasePage {

    //Locators
    @FindBy(id = "captchacharacters")
    private static WebElement captchaInputField;

    public static WebElement getCaptchaInputField() {
        return captchaInputField;
    }

    //Tests
    public void classSpecificMethod() {
        //....
    }
}
