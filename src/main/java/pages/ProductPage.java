package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.WaitUtils;

import java.util.List;

public class ProductPage {
    private final WebDriver driver;
    private final WaitUtils waitUtils;

    private final By productItems = By.className("shelf-item");
    private final By addToCartButton = By.className("shelf-item__buy-btn");
    private final By cartIcon = By.cssSelector(".bag, .bag.bag--float-cart-closed");
    private final By cartCount = By.cssSelector(".bag__quantity");

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
    }

    public int getVisibleProductCount() {
        return driver.findElements(productItems).size();
    }

    public void addProductToCart(int productIndex) {
        List<WebElement> products = driver.findElements(productItems);
        if (productIndex < 0 || productIndex >= products.size()) {
            throw new IllegalArgumentException("Product index is out of range");
        }
        WebElement product = products.get(productIndex);
        WebElement addButton = product.findElement(addToCartButton);
        addButton.click();
    }

    public int getCartCount() {
        try {
            String countText = waitUtils.waitForElementToBeVisible(cartCount).getText();
            return Integer.parseInt(countText.trim());
        } catch (Exception e) {
            return 0;
        }
    }

    public void openCart() {
        waitUtils.waitForElementToBeClickable(cartIcon).click();
    }
}
