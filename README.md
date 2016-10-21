# IS2545 - DELIVERABLE 3: Cucumber Testing
### Jinrong Liu - jil181

    Test Enviornment:    
    NetBeans 8.1 IDE     
    Cucumber - 1.2.5    
    JUnit - 4.1.2    
    geckodriver-v0.11.1-macos

The e-commerce website which is tested is http://store.demoqa.com/

### ISSUES

1. Java Environment Problem

    I got the following error when I firstly run the test file:   
    "Exception in thread "main" java.lang.UnsupportedClassVersionError: org/openqa/selenium/WebDriver : Unsupported major.minor version 52.0."    
    I was using JavaSE - 1.7 which led to the java version mismatch.
    
    Solution: Use JavaSE -1.8 instead. 
    
    Reference: http://stackoverflow.com/questions/22489398/unsupported-major-minor-version-52-0
    
2. WebDriver Problem

   I had issues getting the WebDriver working on my machine and I got the error message:    
   "Exception in thread "main" java.lang.IllegalStateException: The path to the driver executable must be set by the webdriver.gecko.driver system property".
   
   Solution: Since Windows and Mac are using different geckodriver, I have to download a Mac version geckodriver from https://github.com/mozilla/geckodriver/releases,
   and put the geckodriver under the lib folder of the project then change the path to "System.setProperty("webdriver.gecko.driver", "libs/geckodriver");".
   
### USER STORIES
Feature: LogIn Action Test

        Scenario: Successful Login with Valid Credentials
                Given User is on Home Page
                When User Navigate to LogIn Page
                And User enters UserName and Password
                Then Message displayed Login Successfully

        Scenario: Failed to Login with Wrong Passwords
                Given User is on Home Page
                When User Navigate to LogIn Page
                And User enters UserName and Wrong Password
                Then Message displayed Login Failed

        Scenario: Failed to Login with Invalid Username
                Given User is on Home Page
                When User Navigate to LogIn Page
                And User enters Invalid Username
                Then Page displayed Login Error Message

Feature: Search Products

        Scenario: Search an Available Product
                Given User is on Home Page
                When User enter Product Name
                Then Page displayed related products

        Scenario: Search a Nonexistent Product
                Given User is on Home Page
                When User enter irrelevant product name
                Then Page displayed message no product matched

Feature: Purchase Products

        Scenario: Search a product and add the first result/product to the User cart
                Given User searched for laptop
                When User add the first laptop that appears in the search result to the cart
                And User navigate to cart page
                Then User cart should display with 1 item

        Scenario: Remove a Product
                Given User searched for laptop
                When User add the first laptop that appears in the search result to the cart
                And User navigate to cart page
                And click on the remove button
                Then the cart should be empty

        Scenario: Change Quantity of a Product
                Given User searched for laptop
                When User add the first laptop that appears in the search result to the cart
                And User navigate to cart page
                And User modify the quantity of product
                Then the quantity of product and the total price should be updated

        Scenario: Payment Page
                Given User searched for laptop
                When User add the first laptop that appears in the search result to the cart
                And User navigate to cart page
                And User click on Next Button
                Then the page should navigate to the Checkout information page
