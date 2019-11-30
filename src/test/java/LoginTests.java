import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;


public class LoginTests {
    private static final String URL = "https://www.tut.by/";
    private static final By LOGIN_LINK = By.className("enter");
    private static final By LOGIN = By.name("login");
    private static final By PASSWORD = By.name("password");
    private static final By LOGIN_BUTTON = By.xpath(".//input[contains(@class, 'button auth__enter')]");
    private static final By LOGGED_IN_RESULT = By.xpath(".//a[contains(@class, 'enter logedin')]");
    private static final By SELENIUM_TEST_NAME = By.cssSelector(".uname");
    private WebDriver driver;
    WebElement seleniumTestName;

    @BeforeEach
    public void openBrowser() {
        driver = new FirefoxDriver();
        driver.get(URL);
    }

    @AfterEach
    public void closeBrowser() {
        driver.close();
    }

    @Test
    public void loginTest1() {
        driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);
        driver.findElement(LOGIN_LINK).click();
        driver.findElement(LOGIN).sendKeys("seleniumtests@tut.by");
        driver.findElement(PASSWORD).sendKeys("123456789zxcvbn");
        driver.findElement(LOGIN_BUTTON).click();
        Assertions.assertTrue(driver.findElement(LOGGED_IN_RESULT).isDisplayed(), "Element is not found");

    }

    //Test with Thread.sleep (implicit wait)
    @Test
    public void loginTest2() throws InterruptedException {
        Thread.sleep(6000);
        driver.findElement(LOGIN_LINK).click();
        driver.findElement(LOGIN).sendKeys("seleniumtests@tut.by");
        driver.findElement(PASSWORD).sendKeys("123456789zxcvbn");
        driver.findElement(LOGIN_BUTTON).click();
        Assertions.assertTrue(driver.findElement(LOGGED_IN_RESULT).isDisplayed(), "Element is not found");

    }

    @Test
    public void loginTest3() {
        driver.findElement(LOGIN_LINK).click();
        driver.findElement(LOGIN).sendKeys("seleniumtests@tut.by");
        driver.findElement(PASSWORD).sendKeys("123456789zxcvbn");
        driver.findElement(LOGIN_BUTTON).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.pollingEvery(Duration.ofMillis(2000));
        seleniumTestName = wait.until(ExpectedConditions.presenceOfElementLocated(SELENIUM_TEST_NAME));
        Assertions.assertTrue(seleniumTestName.isDisplayed(), "Element is not found");

    }

    @ParameterizedTest
    @CsvFileSource(resources = "/testData.csv", numLinesToSkip = 1)
    public void loginTest4(String username, String password) {
        driver.findElement(LOGIN_LINK).click();
        driver.findElement(LOGIN).sendKeys(username);
        driver.findElement(PASSWORD).sendKeys(password);
        driver.findElement(LOGIN_BUTTON).click();
        WebDriverWait wait = new WebDriverWait(driver, 5);
        seleniumTestName = wait.until(ExpectedConditions.presenceOfElementLocated(SELENIUM_TEST_NAME));
        Assertions.assertTrue(seleniumTestName.isDisplayed(), "Element is not found");
    }
}