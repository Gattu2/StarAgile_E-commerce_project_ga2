package tests;

import com.aventstack.extentreports.Status;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ConfigReader;

public class LoginTest extends BaseTest {

    @Test(description = "TC_001: Login with valid credentials")
    public void loginWithValidCredentials() {
        test = extent.createTest("Login with valid credentials");
        System.out.println("STEP: Starting valid login test");
        test.log(Status.INFO, "Starting valid login test");

        loginPage.login(ConfigReader.getProperty("valid.username"), ConfigReader.getProperty("valid.password"));
        System.out.println("STEP: Submitted valid username and password");
        test.log(Status.INFO, "Submitted valid username and password");

        boolean success = loginPage.isLoginSuccessful();
        System.out.println("STEP: Login success status = " + success);
        test.log(Status.INFO, "Login success status = " + success);
        Assert.assertTrue(success, "Login should succeed with valid credentials");
    }

    @Test(description = "TC_002: Login with invalid credentials")
    public void loginWithInvalidCredentials() {
        test = extent.createTest("Login with invalid credentials");
        System.out.println("STEP: Starting invalid login test");
        test.log(Status.INFO, "Starting invalid login test");

        loginPage.login(ConfigReader.getProperty("invalid.username"), ConfigReader.getProperty("invalid.password"));
        System.out.println("STEP: Submitted invalid username and password");
        test.log(Status.INFO, "Submitted invalid username and password");

        String errorMessage = loginPage.getErrorMessage();
        System.out.println("STEP: Login error message = " + errorMessage);
        test.log(Status.INFO, "Login error message = " + errorMessage);
        Assert.assertTrue(errorMessage.contains("Invalid"), "Error message should display for invalid login");
    }

    @Test(description = "TC_003: Login with empty username and password")
    public void loginWithEmptyCredentials() {
        test = extent.createTest("Login with empty username and password");
        System.out.println("STEP: Starting empty credentials login test");
        test.log(Status.INFO, "Starting empty credentials login test");

        loginPage.login("", "");
        System.out.println("STEP: Submitted empty username and password");
        test.log(Status.INFO, "Submitted empty username and password");

        String errorMessage = loginPage.getErrorMessage();
        System.out.println("STEP: Empty login error message = " + errorMessage);
        test.log(Status.INFO, "Empty login error message = " + errorMessage);
        Assert.assertTrue(errorMessage.contains("required") || errorMessage.length() > 0,
                "Validation message should display when credentials are empty");
    }
}
