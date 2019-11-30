import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class AlertTests {
    private static final String URL = "https://the-internet.herokuapp.com/javascript_alerts";
    private static final By ALERT = By.xpath("//button[text()='Click for JS Alert']");
    private static final By CONFIRM_ALERT = By.xpath("//button[text()='Click for JS Confirm']");
    private static final By PROMPT_ALERT = By.xpath("//button[text()='Click for JS Prompt']");

    private static final By MESSAGE = By.cssSelector("#result");

    private static final String ALERT_EXPECTED = "You successfuly clicked an alert";
    private static final String CONFIRM_EXPECTED = "You clicked: Ok";
    private static final String PROMPT_EXPECTED = "You entered: Nastya";

    private WebDriver driver;
    private Alert alert;

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
    public void jsAlert() {
        driver.findElement(ALERT).click();

        alert = driver.switchTo().alert();
        alert.accept();

        Assertions.assertEquals(ALERT_EXPECTED, driver.findElement(MESSAGE).getText());
    }

    @Test
    public void jsConfirm() {
        driver.findElement(CONFIRM_ALERT).click();

        alert = driver.switchTo().alert();
        alert.accept();

        Assertions.assertEquals(CONFIRM_EXPECTED, driver.findElement(MESSAGE).getText());
    }

    @Test
    public void jsPrompt() {
        driver.findElement(PROMPT_ALERT).click();

        alert = driver.switchTo().alert();
        alert.sendKeys("Nastya");
        alert.accept();

        Assertions.assertEquals(PROMPT_EXPECTED, driver.findElement(MESSAGE).getText());
    }
}
