# Automation Testing Framework for bstackdemo.com

## Project Overview
Automation Testing Framework for `bstackdemo.com` using Java, Selenium WebDriver, TestNG, Maven, and ExtentReports.

## Domain
E-Commerce Web Application

## Application Under Test (AUT)
- Website: https://bstackdemo.com/
- Description: A sample e-commerce application provided by BrowserStack that demonstrates cross-browser testing and standard shopping workflows.

## Objective
Design and implement a modular, scalable automation framework using Page Object Model (POM) to validate login, product catalog, cart, and checkout workflows.

## Tools and Technologies
- Java
- Selenium WebDriver
- TestNG
- Maven
- ExtentReports
- Git / GitHub

## Framework Architecture
- Page Object Model (POM)
- `BaseTest.java` for WebDriver and report initialization
- Page classes: `LoginPage`, `ProductPage`, `CartPage`, `CheckoutPage`
- Utilities: `ConfigReader`, `WebDriverFactory`, `WaitUtils`, `ExtentManager`
- Test classes: `LoginTest`, `AddToCartTest`, `CheckoutTest`
- `testng.xml` for suite execution

## Project Structure
```
src/
 ├── main/
 │   └── java/
 │       ├── pages/
 │       │   ├── LoginPage.java
 │       │   ├── ProductPage.java
 │       │   ├── CartPage.java
 │       │   └── CheckoutPage.java
 │       └── utils/
 │           ├── ConfigReader.java
 │           ├── WebDriverFactory.java
 │           ├── WaitUtils.java
 │           └── ExtentManager.java
 ├── test/
 │   ├── java/
 │   │   ├── tests/
 │   │   │   ├── BaseTest.java
 │   │   │   ├── LoginTest.java
 │   │   │   ├── AddToCartTest.java
 │   │   │   └── CheckoutTest.java
 │   └── resources/
 │       └── config.properties
testng.xml
```

## Test Scenarios Covered
### Login Tests
- `TC_001` Login with valid credentials
- `TC_002` Login with invalid credentials
- `TC_003` Login with empty username/password

### Cart Tests
- `TC_004` Add single item to cart
- `TC_005` Add multiple items to cart and verify cart count
- `TC_006` Remove item from cart

### Checkout Tests
- `TC_007` Place order with valid details
- `TC_008` Checkout flow without adding items (negative test)

## Execution
### Command Line
```bash
mvn clean test
```

### IDE
Run `testng.xml` from your IDE or use Maven Surefire.

## Reporting
- ExtentReports HTML reports are generated under `test-output/extent-report.html`
- TestNG default reports are generated under `target/surefire-reports/`
