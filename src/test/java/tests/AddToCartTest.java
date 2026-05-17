package tests;

import com.aventstack.extentreports.Status;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.ProductPage;
import utils.ConfigReader;

public class AddToCartTest extends BaseTest {

    @Test(description = "TC_004: Add single item to cart")
    public void addSingleItemToCart() {
        test = extent.createTest("Add single item to cart");
        System.out.println("STEP: Starting add single item to cart test");
        test.log(Status.INFO, "Starting add single item to cart test");

        loginPage.login(ConfigReader.getProperty("valid.username"), ConfigReader.getProperty("valid.password"));
        System.out.println("STEP: Logged in with valid credentials");
        test.log(Status.INFO, "Logged in with valid credentials");

        ProductPage productPage = new ProductPage(driver);
        System.out.println("STEP: Adding first product to cart");
        test.log(Status.INFO, "Adding first product to cart");
        productPage.addProductToCart(0);

        System.out.println("STEP: Opening cart");
        test.log(Status.INFO, "Opening cart");
        productPage.openCart();

        CartPage cartPage = new CartPage(driver);
        int itemCount = cartPage.getCartItemCount();
        System.out.println("STEP: Cart item count after add = " + itemCount);
        test.log(Status.INFO, "Cart item count after add = " + itemCount);
        Assert.assertEquals(itemCount, 1, "Cart should contain a single item");
    }

    @Test(description = "TC_005: Add multiple items to cart and verify cart count")
    public void addMultipleItemsToCart() {
        test = extent.createTest("Add multiple items to cart and verify cart count");
        System.out.println("STEP: Starting add multiple items to cart test");
        test.log(Status.INFO, "Starting add multiple items to cart test");

        loginPage.login(ConfigReader.getProperty("valid.username"), ConfigReader.getProperty("valid.password"));
        System.out.println("STEP: Logged in with valid credentials");
        test.log(Status.INFO, "Logged in with valid credentials");

        ProductPage productPage = new ProductPage(driver);
        System.out.println("STEP: Adding two products to cart");
        test.log(Status.INFO, "Adding two products to cart");
        productPage.addProductToCart(0);
        productPage.addProductToCart(1);

        int cartCount = productPage.getCartCount();
        System.out.println("STEP: Cart count after adding products = " + cartCount);
        test.log(Status.INFO, "Cart count after adding products = " + cartCount);
        Assert.assertTrue(cartCount >= 2, "Cart count should reflect multiple added items");
    }

    @Test(description = "TC_006: Remove item from cart")
    public void removeItemFromCart() {
        test = extent.createTest("Remove item from cart");
        System.out.println("STEP: Starting remove item from cart test");
        test.log(Status.INFO, "Starting remove item from cart test");

        loginPage.login(ConfigReader.getProperty("valid.username"), ConfigReader.getProperty("valid.password"));
        System.out.println("STEP: Logged in with valid credentials");
        test.log(Status.INFO, "Logged in with valid credentials");

        ProductPage productPage = new ProductPage(driver);
        System.out.println("STEP: Adding a product to cart for removal");
        test.log(Status.INFO, "Adding a product to cart for removal");
        productPage.addProductToCart(0);

        System.out.println("STEP: Opening cart to remove item");
        test.log(Status.INFO, "Opening cart to remove item");
        productPage.openCart();

        CartPage cartPage = new CartPage(driver);
        int initialCount = cartPage.getCartItemCount();
        System.out.println("STEP: Initial cart item count = " + initialCount);
        test.log(Status.INFO, "Initial cart item count = " + initialCount);
        if (initialCount > 0) {
            cartPage.removeItem(0);
            System.out.println("STEP: Removed the first item from cart");
            test.log(Status.INFO, "Removed the first item from cart");
        }
        int finalCount = cartPage.getCartItemCount();
        System.out.println("STEP: Final cart item count = " + finalCount);
        test.log(Status.INFO, "Final cart item count = " + finalCount);
        Assert.assertTrue(finalCount < initialCount, "Cart item count should decrease after removal");
    }
}
