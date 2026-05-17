package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.WaitUtils;

import java.time.Duration;
import java.util.List;

public class LoginPage {
    private final WebDriver driver;
    private final WaitUtils waitUtils;

    private final By usernameField = By.id("react-select-2-input");
    private final By usernameOptions = By.cssSelector("[id^=\"react-select-2-option-\"]");

    private final By passwordField = By.id("react-select-3-input");
    private final By passwordOptions = By.cssSelector("[id^=\"react-select-3-option-\"]");

    private final By loginButton = By.id("login-btn");
    private final By errorMessage = By.cssSelector("h3, .auth-error");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
    }

    public void login(String username, String password) {
        selectDropdownValue(usernameField, usernameOptions, username);
        selectDropdownValue(passwordField, passwordOptions, password);
        waitUtils.waitForElementToBeClickable(loginButton).click();
    }

    private void selectDropdownValue(By inputLocator, By optionsLocator, String value) {
        WebElement input = waitUtils.waitForElementToBeVisible(inputLocator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", input);
        input.sendKeys("");

        if (value != null && !value.trim().isEmpty()) {
            input.sendKeys(value);
            try {
                WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
                List<WebElement> options = shortWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(optionsLocator));
                for (WebElement option : options) {
                    if (option.getText().trim().equals(value)) {
                        option.click();
                        return;
                    }
                }
            } catch (Exception ignored) {
                // no matching dropdown option found
            }
            input.sendKeys(Keys.ESCAPE);
        }
    }

    public String getErrorMessage() {
        try {
            WebElement error = waitUtils.waitForElementToBeVisible(errorMessage);
            return error.getText();
        } catch (Exception e) {
            return "No error message found";
        }
    }

    public boolean isLoginSuccessful() {
        try {
            Thread.sleep(2000);
            String currentUrl = driver.getCurrentUrl();
            if (currentUrl.contains("signin=true")) {
                return true;
            }
            if (!currentUrl.contains("/signin")) {
                return true;
            }
            return driver.getPageSource().contains("Logout");
        } catch (Exception e) {
            return false;
        }
    }
}
