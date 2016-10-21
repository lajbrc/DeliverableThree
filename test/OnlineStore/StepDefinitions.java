/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OnlineStore;

import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.util.concurrent.TimeUnit;
import static junit.framework.Assert.assertEquals;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author AsphaltPanthers
 */
public class StepDefinitions {
    private WebDriver driver;
    private WebDriverWait wait;
    
    private final String HOME_PAGE = "http://store.demoqa.com/";
    private final String CART_PAGE = "http://store.demoqa.com/products-page/checkout/";
    private final String emptyCart = "Oops, there is nothing in your cart. ";
    private final String pwErrorMsg = ": Invalid login credentials.";
    private final String pwEmpty = ": The password field is empty.";
    
    @Given("a Firefox browser")
    public void openFirefox(){
        System.setProperty("webdriver.gecko.driver", "libs/geckodriver");
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, 30);
    }
    
    @When("I navigate to the home page")
    public void navigateHome(){
        driver.get(HOME_PAGE);
    }
    
    @Then("the title should be (.*)")
    public void checkPageTitle(String title){
        assertEquals(title, driver.getTitle());
    }
    
    
    //Feature: LogIn Action Test
    //Scenario1: Successful Login with Valid Credentials
    @Given("User is on Home Page")
    public void HomePage() throws Throwable{
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, 30);
        driver.get(HOME_PAGE);
    }
    
    @When("User Navigate to LogIn Page")
    public void user_Navigate_to_LogIn_Page() throws Throwable {
        driver.findElement(By.xpath(".//*[@id='account']/a")).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
   
    @And("User enters UserName and Password")
    public void user_enters_UserName_and_Password() throws Throwable {
        driver.findElement(By.id("log")).sendKeys("testuserliu"); 	 
	driver.findElement(By.id("pwd")).sendKeys("Test@lajbrc");
	driver.findElement(By.id("login")).click();
    }
  
    @Then("Message displayed Login Successfully")
    public void message_displayed_Login_Successfully() throws Throwable {
        System.out.println("Login Successfully");
    }
    
    //Scenario2: Failed to Login with Wrong Passwords
    @And("User enters UserName and Wrong Password")
    public void user_enters_UserName_and_Wrong_Password() throws Throwable {
        driver.findElement(By.id("log")).sendKeys("testuserliu"); 	 
	driver.findElement(By.id("pwd")).sendKeys("123");
	driver.findElement(By.id("login")).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
    
    @Then("Message displayed Login Failed")
    public void message_displayed_Login_Failed() throws Throwable {
        String errorMsg1 = driver.findElement(By.xpath(".//*[@class='response']")).getText();
        assertEquals(pwErrorMsg, errorMsg1);     
    }
    
    //Scenario3: Failed to Login with Invalid Username
    @And("User enters Invalid Username")
    public void user_enters_invalid_username() {
        driver.findElement(By.id("log")).sendKeys("testhahaha"); 	 
	driver.findElement(By.id("login")).click();
    }
    
    @Then("Page displayed Login Error Message")
    public void page_displayed_login_error_message(){
        String errorMsg2 = driver.findElement(By.xpath(".//*[@class='response']/a")).getText();
        assertEquals(pwEmpty, errorMsg2);
    }
    
    //Feature: Search Products
    //Scenario1: Search an Available Product
    @When("User enter Product Name")
    public void user_enters_prodcut_name(){
        driver.findElement(By.xpath(".//*[@class='searchform']/a")).sendKeys("phone");
    }
    
    @Then("Page displayed related products")
    public void page_displayed_related_product(){
        
    }
    
    //Scenario2: Search a Nonexistent Product
    @When("User enter irrelevant product name")
    public void user_enter_irrelevant_product_name(){
        driver.findElement(By.xpath(".//*[@class='searchform']/a")).sendKeys("scarf");
    }
    
    @Then("Page displayed message no product matched")
    public void page_displayed_message_no_product_matched(){
        driver.findElement(By.id("content")).getText(); 
    }
    
    //Feature: Purchase Products
    //Scenario1: Search a product and add the first result/product to the User cart
    @Given("User searched for laptop")
    public void user_searched_for_product(){
        driver.get(HOME_PAGE);
        driver.findElement(By.xpath(".//*[@class='searchform']/a")).sendKeys("laptop");
        driver.findElement(By.xpath(".//*[@class='searchform']/a")).submit();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);      
    }
    
    @When("User add the first laptop that appears in the search result to the cart")
    public void add_the_first_product_to_the_cart(){
        driver.findElement(By.xpath(".//*[@class='wpsc_buybutton_']")).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
    
    @And("User navigate to cart page")
    public void user_navigate_to_cart_page(){
        driver.findElement(By.xpath(".//*[@class='go_to_checkout']")).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
    
    @Then("User cart should display with 1 item")
    public void cart_count(){
//        WebElement count = driver.findElement(By.xpath(".//*[@class='count']"));
//        assertEquals(count.getText(), "1");
        String quantity1 = driver.findElement(By.xpath(".//*[@name='quantity']")).getCssValue("value");
        assertEquals("1", quantity1);
    }
    
    //Scenario2: Remove a Product
//    @Given("User has one product in the cart")
//    public void user_have_product_in_cart(){
//        driver.get(CART_PAGE);
//        String quantity1 = driver.findElement(By.xpath(".//*[@name='quantity']")).getCssValue("value");
//        assertEquals("1", quantity1);
//    }
    
    @And("click on the remove button")
    public void click_on_remove_button(){
        driver.findElement(By.xpath(".//*[@value='remove']/a")).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
    
    @Then("the cart should be empty")
    public void empty_cart(){
        String empty = driver.findElement(By.xpath(".//*[@class='entry-content']")).getText();
        assertEquals(emptyCart, empty);
    }
    
    //Scenario3: Change Quantity of a Product 
    @When("user modify the quantity of product")
    public void change_quantity(){
        driver.findElement(By.xpath(".//*[@name='quantity']/a")).sendKeys("2");
        driver.findElement(By.xpath(".//*[@value='Update']")).click();
    }
    
    @Then("the quantity of product and the total price should be updated")
    public void quantity_and_price_updated(){
        String quantity2 = driver.findElement(By.xpath(".//*[@name='quantity']")).getCssValue("value");
        assertEquals("2", quantity2);
    }
    
    //Scenario4: Payment Page
    @When("User click on Next Button")
    public void user_click_next_button(){
        driver.findElement(By.xpath(".//*[@class='wpsc_buybutton_']")).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
    
    @Then("the page should navigate to the Checkout information page")
    public void navigate_to_checkout_information_page(){
        String ship = driver.findElement(By.xpath(".//*[@class='wpsc_billing_forms ']")).getCssValue("h4");
        assertEquals("Your billing/contact details", ship);
    }
    
    @After
    public void cleanUp(){
        driver.quit();
    }
}
