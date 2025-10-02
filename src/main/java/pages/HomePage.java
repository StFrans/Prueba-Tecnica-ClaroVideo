package pages;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

    private WebDriver driver;
    private WebDriverWait wait;
    private ExtentTest test;
    private static final String BASE_URL = "https://www.mercadolibre.com/";

    public HomePage(WebDriver driver, WebDriverWait wait, ExtentTest test) {
        this.driver = driver;
        this.wait = wait;
        this.test = test;
    }

    public void openSite() {
        driver.get(BASE_URL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));
        test.info("Sitio cargado correctamente.");
    }

    public void selectCountry(String country) {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'" + country + "')]"))).click();
        test.info("País seleccionado: " + country);
    }

    public void acceptCookiesIfPresent() {
        try {
            WebElement cookiesBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.cookie-consent-banner-opt-out__action")));
            cookiesBtn.click();
            test.info("Cookies aceptadas.");
        } catch (TimeoutException e) {
            test.info("No apareció banner de cookies.");
        }
    }
}
