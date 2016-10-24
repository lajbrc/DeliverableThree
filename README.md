# IS2545 - DELIVERABLE 3: Cucumber Testing
### Jinrong Liu - jil181

    Test Enviornment:    
    NetBeans 8.1 IDE     
    Cucumber - 1.2.5    
    JUnit - 4.1.2    
    geckodriver-v0.11.1-macos

The e-commerce website which was tested is http://store.demoqa.com/

### Configuration Issues

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

### Test Problems

1. Error: Unable to locate element

   I got this error when I tried to find the error message when the login failed.
   
   Solution: Add "driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);" after "driver.findElement(By.id("login")).click();".
   Since I did a click action before I tried to find this error message, I had to put a wait after the click action, which means that any search for elements on the page could take the time the wait is set for before throwing exception.
   
2. Error: Get unexpected text
   
   I got this error in the LogIn Test after I add the implicit wait. The return value is [] instead of expected error message.
   
   Solution: I changed the xpath for finding the expected error message from "driver.findElement(By.xpath(“.//*[@class=‘response']")).getText();" to "String errorMsg1 = driver.findElement(By.xpath(".//*[@id='ajax_loginform']/p[1]/strong")).getText();"
   
3. Problem: Test failed for all PurchaseProducts feature

   I was able to get the laptop page, add the laptop to cart, and navigate to the cart page, but all the following tests failed and the webpage crashed afer navigating to the cart page.
   
   Solution: I have to put a "Thread.sleep();" to suspend the current thread for a while after navigating to the cart page.

4. Problem: Unable to correctly change the quantity of product in cart
   
   I want to update the quantity of product from 1 to 2, but what I got was 12.
   
   Solution: Since sendKeys() function simply add "2" after "1", I have to clear the field before changing the number of products. Add "driver.findElement(By.xpath(".//*[@name='quantity']")).clear();" before updating the product quantity.
   
   Reference: http://stackoverflow.com/questions/3249583/selenium-webdriver-i-want-to-overwrite-value-in-field-instead-of-appending-to-i

5. Problem: Cannot test a search function in website

    I want to test the search function in this online store, but there is no button to click for submitting after I sent keywords to the search bar, I have to figure out how to typing Enter key in Selenium test.
    
    Solution: Add a .sendKeys(Keys.ENTER) after sending the keywords to search bar.    
    "WebElement search = driver.findElement(By.className("search"));    
    search.sendKeys("phone");    
    search.sendKeys(Keys.ENTER);"
    
    Reference: http://stackoverflow.com/questions/1629053/typing-enter-return-key-in-selenium

### User Stories
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

        Scenario: Add a product to cart
                Given User in the laptop page
                When User add the laptop to the cart
                And User navigate to cart page
                Then User cart should display with 1 item

        Scenario: Remove a Product
                Given User in the laptop page
                When User add the laptop to the cart
                And User navigate to cart page
                And click on the remove button
                Then the cart should be empty

        Scenario: Change Quantity of a Product
                Given User in the laptop page
                When User add the laptop to the cart
                And User navigate to cart page
                And User modify the quantity of product
                Then the quantity of product and the total price should be updated

        Scenario: Payment Page
                Given User in the laptop page
                When User add the laptop to the cart
                And User navigate to cart page
                And User click on Continue Button
                Then the page should navigate to the Checkout information page
