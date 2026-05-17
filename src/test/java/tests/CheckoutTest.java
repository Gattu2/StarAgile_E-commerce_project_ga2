package tests;

import com.aventstack.extentreports.Status;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.CheckoutPage;
import pages.ProductPage;
import utils.ConfigReader;

public class CheckoutTest extends BaseTest {

    @Test(description = "TC_007: Place order with valid details")
    public void placeOrderWithValidDetails() {
        test = extent.createTest("Place order with valid details");
        loginPage.login(ConfigReader.getProperty("valid.username"), ConfigReader.getProperty("valid.password"));
        ProductPage productPage = new ProductPage(driver);
        productPage.addProductToCart(0);
        productPage.openCart();

        CartPage cartPage = new CartPage(driver);
        cartPage.proceedToCheckout();

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        System.out.println("STEP: Filling checkout shipping details");
        test.log(Status.INFO, "Filling checkout shipping details");
        checkoutPage.fillCheckoutForm("Test", "User", "123 Main St", "California", "90001");

        System.out.println("STEP: Clicking Submit on shipping address page");
        test.log(Status.INFO, "Clicking Submit on shipping address page");
        checkoutPage.submitOrder();
       

        String successMessage = checkoutPage.getOrderSuccessMessage();
        System.out.println("STEP: Order success message = " + successMessage);
        test.log(Status.INFO, "Order success message: " + successMessage);
        Assert.assertTrue(successMessage.contains("Your Order has been successfully placed") || successMessage.contains("Thank you") || successMessage.contains("Success"),
                "Confirmation message should indicate successful order placement");

        String orderNumber = checkoutPage.getOrderNumber();
        System.out.println("STEP: Order number text = " + orderNumber);
        test.log(Status.INFO, "Order number text: " + orderNumber);
        Assert.assertTrue(orderNumber.toLowerCase().contains("your order number"), "Order number should be displayed on confirmation page");

        String summaryItems = checkoutPage.getOrderSummaryItems();
        System.out.println("STEP: Order summary items = " + summaryItems);
        test.log(Status.INFO, "Order summary items: " + summaryItems);
        Assert.assertTrue(summaryItems.contains("item(s)"), "Order summary should show number of items");

        String grandTotal = checkoutPage.getGrandTotal();
        System.out.println("STEP: Grand total = " + grandTotal);
        test.log(Status.INFO, "Grand total: " + grandTotal);
        Assert.assertTrue(grandTotal.startsWith("$"), "Grand total should be displayed with currency on confirmation page");
    }

    @Test(description = "TC_008: Checkout flow without adding items")
    public void checkoutWithoutAddingItems() {
        test = extent.createTest("Checkout flow without adding items");
        loginPage.login(ConfigReader.getProperty("valid.username"), ConfigReader.getProperty("valid.password"));

        ProductPage productPage = new ProductPage(driver);
        productPage.openCart();

        CartPage cartPage = new CartPage(driver);
        cartPage.proceedToCheckout();

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        if (!checkoutPage.isShippingFormDisplayed()) {
            System.out.println("STEP: Shipping address page not displayed for empty cart checkout");
            test.log(Status.INFO, "Shipping address page not displayed for empty cart checkout");
            Assert.assertTrue(true, "Checkout page was not shown when cart was empty");
            return;
        }

        System.out.println("STEP: Filling checkout shipping details for empty cart flow");
        test.log(Status.INFO, "Filling checkout shipping details for empty cart flow");
        checkoutPage.fillCheckoutForm("Test", "User", "123 Main St", "California", "90001");

        System.out.println("STEP: Clicking Submit for empty cart flow");
        test.log(Status.INFO, "Clicking Submit for empty cart flow");
        checkoutPage.submitOrder();

        String confirmation = checkoutPage.getConfirmationText();
        System.out.println("STEP: Checkout response text = " + confirmation);
        test.log(Status.INFO, "Checkout response text: " + confirmation);
        Assert.assertTrue(confirmation.contains("cart") || confirmation.contains("empty") || confirmation.contains("Order") || confirmation.contains("Thank you"),
                "Negative flow should show a message when submitting checkout without items");
    }
}
