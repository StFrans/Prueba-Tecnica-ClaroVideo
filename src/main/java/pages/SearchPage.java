package pages;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SearchPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private ExtentTest test;

    public SearchPage(WebDriver driver, WebDriverWait wait, ExtentTest test) {
        this.driver = driver;
        this.wait = wait;
        this.test = test;
    }

    public void searchProduct(String product) {
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("as_word")));
        searchBox.sendKeys(product);
        searchBox.submit();
        test.info("Búsqueda realizada: " + product);
    }

    public void filterByCondition(String condition) {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@href, '" + condition + "')]"))).click();
        test.info("Filtro aplicado: " + condition);
    }

    public void applyPriceRange(String min, String max) {
        WebElement inputMin = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-testid='Minimum-price']")));
        inputMin.clear();
        inputMin.sendKeys(min);

        WebElement inputMax = driver.findElement(By.cssSelector("[data-testid='Maximum-price']"));
        inputMax.clear();
        inputMax.sendKeys(max);
        inputMax.sendKeys(Keys.ENTER);

        test.info("Rango de precios aplicado: $" + min + " - $" + max);
    }
}
