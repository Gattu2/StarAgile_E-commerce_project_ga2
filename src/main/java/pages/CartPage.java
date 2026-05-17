package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.WaitUtils;

import java.util.List;

public class CartPage {
    private final WebDriver driver;
    private final WaitUtils waitUtils;

    private final By cartItems = By.cssSelector(".float-cart--open .shelf-item");
    private final By removeButtons = By.cssSelector(".shelf-item__del");
    private final By totalPrice = By.cssSelector(".float-cart__footer");
    private final By checkoutButton = By.cssSelector(".buy-btn");

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
    }

    public int getCartItemCount() {
        try {
            return driver.findElements(cartItems).size();
        } catch (Exception e) {
            return 0;
        }
    }

    public void removeItem(int index) {
        List<WebElement> buttons = driver.findElements(removeButtons);
        if (index < 0 || index >= buttons.size()) {
            throw new IllegalArgumentException("Remove index is out of range");
        }
        buttons.get(index).click();
    }

    public String getTotalPrice() {
        try {
            return waitUtils.waitForElementToBeVisible(totalPrice).getText();
        } catch (Exception e) {
            return "0";
        }
    }

    public void proceedToCheckout() {
        try {
            waitUtils.waitForElementToBeClickable(checkoutButton).click();
        } catch (Exception e) {
            // no checkout button visible
        }
    }
}
