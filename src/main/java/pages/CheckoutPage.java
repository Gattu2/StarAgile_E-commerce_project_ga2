package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.WaitUtils;

public class CheckoutPage {
    private final WebDriver driver;
    private final WaitUtils waitUtils;

    private final By firstNameField = By.xpath("//*[normalize-space()='First Name']/following::input[1]");
    private final By lastNameField = By.xpath("//*[normalize-space()='Last Name']/following::input[1]");
    private final By addressField = By.xpath("//*[normalize-space()='Address']/following::input[1]");
    private final By stateField = By.xpath("//*[normalize-space()='State/Province']/following::input[1]");
    private final By postalCodeField = By.xpath("//*[normalize-space()='Postal Code']/following::input[1]");
    private final By submitButton = By.xpath("//button[normalize-space()='Submit']");
    private final By shippingAddressHeader = By.xpath("//*[normalize-space()='Shipping Address']");
    private final By successHeader = By.xpath("//h1[contains(normalize-space(.),'Your Order has been successfully placed')]|//h2[contains(normalize-space(.),'Your Order has been successfully placed')]|//h3[contains(normalize-space(.),'Your Order has been successfully placed')]");
    private final By orderNumberText = By.xpath("//p[contains(normalize-space(.),'Your order number')]|//div[contains(normalize-space(.),'Your order number')]");
    private final By summaryItemCount = By.xpath("//h3[contains(normalize-space(.),'item(s)')]");
    private final By grandTotalText = By.xpath("//*[normalize-space()='Total (USD)']/following::*[contains(normalize-space(),'$')][1]");

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
    }

    public boolean isShippingFormDisplayed() {
        try {
            return waitUtils.waitForElementToBeVisible(shippingAddressHeader) != null;
        } catch (Exception e) {
            return false;
        }
    }

    public void fillCheckoutForm(String firstName, String lastName, String address, String state, String postalCode) {
        waitUtils.waitForElementToBeVisible(firstNameField).sendKeys(firstName);
        waitUtils.waitForElementToBeVisible(lastNameField).sendKeys(lastName);
        waitUtils.waitForElementToBeVisible(addressField).sendKeys(address);
        waitUtils.waitForElementToBeVisible(stateField).sendKeys(state);
        waitUtils.waitForElementToBeVisible(postalCodeField).sendKeys(postalCode);
    }

    public void submitOrder() {
        waitUtils.waitForElementToBeClickable(submitButton).click();
    }

    public String getOrderSuccessMessage() {
        try {
            WebElement el = waitUtils.waitForElementToBeVisible(successHeader);
            if (el != null) return el.getText().trim();
        } catch (Exception ignored) {}
        try {
            String body = driver.findElement(By.tagName("body")).getText();
            java.util.regex.Matcher m = java.util.regex.Pattern.compile("Your Order has been successfully placed\\.?", java.util.regex.Pattern.CASE_INSENSITIVE).matcher(body);
            if (m.find()) return m.group().trim();
        } catch (Exception ignored) {}
        return "No order success message found";
    }

    public String getOrderNumber() {
        try {
            WebElement el = waitUtils.waitForElementToBeVisible(orderNumberText);
            if (el != null) {
                String txt = el.getText().trim();
                java.util.regex.Matcher m = java.util.regex.Pattern.compile("Your order number is \\d+", java.util.regex.Pattern.CASE_INSENSITIVE).matcher(txt);
                if (m.find()) return m.group().trim();
                return txt;
            }
        } catch (Exception ignored) {}
        try {
            String body = driver.findElement(By.tagName("body")).getText();
            java.util.regex.Matcher m = java.util.regex.Pattern.compile("Your order number is \\d+", java.util.regex.Pattern.CASE_INSENSITIVE).matcher(body);
            if (m.find()) return m.group().trim();
        } catch (Exception ignored) {}
        return "No order number found";
    }

    public String getOrderSummaryItems() {
        try {
            WebElement el = waitUtils.waitForElementToBeVisible(summaryItemCount);
            if (el != null) return el.getText().trim();
        } catch (Exception ignored) {}
        try {
            String body = driver.findElement(By.tagName("body")).getText();
            java.util.regex.Matcher m = java.util.regex.Pattern.compile("\\d+ item\\(s\\)", java.util.regex.Pattern.CASE_INSENSITIVE).matcher(body);
            if (m.find()) return m.group().trim();
        } catch (Exception ignored) {}
        return "No order summary item count found";
    }

    public String getGrandTotal() {
        try {
            return waitUtils.waitForElementToBeVisible(grandTotalText).getText();
        } catch (Exception e) {
            return "No grand total found";
        }
    }

    public String getConfirmationText() {
        try {
            WebElement body = driver.findElement(By.tagName("body"));
            return body.getText();
        } catch (Exception e) {
            return "No confirmation shown";
        }
    }
}
