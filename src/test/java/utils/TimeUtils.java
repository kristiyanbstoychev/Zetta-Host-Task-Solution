package utils;

import org.openqa.selenium.WebElement;

import java.util.List;

import static tests.BaseTest.scrollIntoView;

public class TimeUtils {

    public static void waitForSeconds(int timeoutInSeconds) {
        try {
            Thread.sleep(timeoutInSeconds * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
