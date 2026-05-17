package tests;

import java.awt.Desktop;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import pages.LoginPage;
import utils.ConfigReader;
import utils.ExtentManager;
import utils.WebDriverFactory;

public class BaseTest {
    protected WebDriver driver;
    protected static ExtentReports extent;
    protected ExtentTest test;
    protected LoginPage loginPage;
    protected static String extentReportPath;

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        extentReportPath = "test-output/extent-report_" + timestamp + ".html";
        extent = ExtentManager.createInstance(extentReportPath);
    }

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        // Ensure ExtentReports initialized (fallback if BeforeSuite didn't run)
        if (extent == null) {
            synchronized (BaseTest.class) {
                if (extent == null) {
                    try {
                        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                        extentReportPath = "test-output/extent-report_" + timestamp + ".html";
                        extent = ExtentManager.createInstance(extentReportPath);
                    } catch (Exception ignored) {
                    }
                }
            }
        }

        test = (extent != null) ? extent.createTest("Test") : null;
        try {
            driver = WebDriverFactory.createDriver(ConfigReader.getProperty("browser"));
            driver.get(ConfigReader.getProperty("base.url"));
            loginPage = new LoginPage(driver);
        } catch (Exception e) {
            if (test != null) test.log(Status.FAIL, "Setup failed: " + e.getMessage());
            throw e;
        }
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (test != null) {
            if (result.getStatus() == ITestResult.FAILURE) {
                test.log(Status.FAIL, "Test failed: " + result.getThrowable());
            } else if (result.getStatus() == ITestResult.SKIP) {
                test.log(Status.SKIP, "Test skipped: " + result.getThrowable());
            } else {
                test.log(Status.PASS, "Test passed");
            }
        }
        if (driver != null) {
            driver.quit();
        }
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite() {
        if (extent != null) {
            extent.flush();
        }
        // Attempt to auto-open the report locally (safe to fail on CI)
        if (extentReportPath != null) {
            try {
                File reportFile = new File(extentReportPath);
                if (reportFile.exists() && Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().browse(reportFile.toURI());
                }
            } catch (Exception ignored) {
            }
        }
    }
}
