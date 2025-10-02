package pages;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BaseTest {

    private static final String COUNTRY = "México";
    private static final String SEARCH_TERM = "playstation 5";
    private static final int MAX_PRODUCTS = 5;
    private static final String MIN_PRICE = "8000";
    private static final String MAX_PRICE = "10000";

    private static ExtentReports extent;
    private static ExtentTest test;

    public static void main(String[] args) {
        // Inicializar reportes
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter("report.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            test = extent.createTest("Prueba MercadoLibre", "Automatización de búsqueda de productos");

            HomePage homePage = new HomePage(driver, wait, test);
            SearchPage searchPage = new SearchPage(driver, wait, test);
            ResultsPage resultsPage = new ResultsPage(driver, wait, test);

            homePage.openSite();
            homePage.selectCountry(COUNTRY);
            homePage.acceptCookiesIfPresent();

            searchPage.searchProduct(SEARCH_TERM);
            searchPage.filterByCondition("Nuevo");
            searchPage.applyPriceRange(MIN_PRICE, MAX_PRICE);

            resultsPage.printTopProducts(MAX_PRODUCTS);

            test.pass("Prueba completada sin errores");

        } catch (Exception e) {
            test.fail("Error durante la ejecución: " + e.getMessage());
        } finally {
            driver.quit();
            extent.flush();
        }
    }
}
