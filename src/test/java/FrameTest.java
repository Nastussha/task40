import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FrameTest {
    private static final String URL = "https://the-internet.herokuapp.com/iframe";
    private static final String FRAME_LOCATOR = "mce_0_ifr";
    private static final By ACTIVE_ELEMENT = By.cssSelector("#tinymce");
    private static final By BOLD_BUTTON = By.xpath("//div[contains(@id,'mceu_3')]/button");

    private static final By HELLO_EXPECTED = By.xpath("//p");
    private static final By WORLD_EXPECTED = By.xpath("//strong");

    private WebDriver driver;

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
    public void textValidating() {
        driver.switchTo().frame(FRAME_LOCATOR);
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.findElement(ACTIVE_ELEMENT).clear();

        WebElement activeElement = driver.findElement(ACTIVE_ELEMENT);
        Actions builder = new Actions(driver);

        Action seriesOfActions = builder
                .moveToElement(activeElement)
                .click()
                .sendKeys(activeElement, "Hello world!")
                .keyDown(activeElement, Keys.SHIFT)
                .sendKeys(Keys.ARROW_LEFT)
                .sendKeys(Keys.ARROW_LEFT)
                .sendKeys(Keys.ARROW_LEFT)
                .sendKeys(Keys.ARROW_LEFT)
                .sendKeys(Keys.ARROW_LEFT)
                .sendKeys(Keys.ARROW_LEFT)
                .keyUp(Keys.SHIFT)
                .build();

        seriesOfActions.perform();

        driver.switchTo().defaultContent();
        driver.findElement(BOLD_BUTTON).click();

        driver.switchTo().frame(FRAME_LOCATOR);

        Assertions.assertAll("Should assert that 'Hello world!' is entered and 'world' is in bold",
                () -> assertEquals("Hello world!", driver.findElement(HELLO_EXPECTED).getText(),
                        "'Hello world!' text is not displayed"),
                () -> assertEquals("world!", driver.findElement(WORLD_EXPECTED).getText(),
                        "'world!' text is not displayed")
        );

    }
}
