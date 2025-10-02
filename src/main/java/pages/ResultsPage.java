package pages;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class ResultsPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private ExtentTest test;

    public ResultsPage(WebDriver driver, WebDriverWait wait, ExtentTest test) {
        this.driver = driver;
        this.wait = wait;
        this.test = test;
    }

    public void printTopProducts(int maxProducts) {
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("li.ui-search-layout__item"), 0));
        List<WebElement> items = driver.findElements(By.cssSelector("li.ui-search-layout__item"));

        test.info("Resultados encontrados: " + items.size());
        test.info("Mostrando los primeros " + maxProducts + " productos:");

        for (int i = 0; i < maxProducts && i < items.size(); i++) {
            try {
                WebElement item = driver.findElements(By.cssSelector("li.ui-search-layout__item")).get(i);

                String name = item.findElement(By.className("poly-component__title")).getText();
                String price = item.findElement(By.cssSelector(".andes-money-amount__fraction")).getText();

                System.out.println((i + 1) + ". " + name + " - $" + price);
                test.pass((i + 1) + ". " + name + " - $" + price);

            } catch (StaleElementReferenceException e) {
                test.warning("Producto #" + (i + 1) + " se actualizó en el DOM. Reintentando...");
                i--;
            } catch (NoSuchElementException e) {
                test.warning("No se pudo obtener nombre o precio del producto #" + (i + 1));
            }
        }
    }
}
