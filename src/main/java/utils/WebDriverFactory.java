package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;

public class WebDriverFactory {
    public static WebDriver createDriver(String browser) {
        if (browser == null || browser.isEmpty()) {
            browser = "chrome";
        }

        try {
            switch (browser.toLowerCase()) {
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    return configureDriver(new FirefoxDriver());
                case "edge":
                    WebDriverManager.edgedriver().setup();
                    return configureDriver(new EdgeDriver());
                default:
                    WebDriverManager.chromedriver().setup();
                    return configureDriver(new ChromeDriver());
            }
        } catch (Exception e) {
            System.err.println("WebDriverManager failed, attempting local driver: " + e.getMessage());
            return configureDriver(new ChromeDriver());
        }
    }

    private static WebDriver configureDriver(WebDriver driver) {
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Integer.parseInt(ConfigReader.getProperty("implicit.wait"))));
        return driver;
    }
}
