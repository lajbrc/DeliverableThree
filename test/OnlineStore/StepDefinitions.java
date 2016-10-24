/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OnlineStore;

import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.util.concurrent.TimeUnit;
import static junit.framework.Assert.assertEquals;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author AsphaltPanthers
 * @author lajbrc
 */
public class StepDefinitions {
    private WebDriver driver;
    private WebDriverWait wait;
    
    private final String HOME_PAGE = "http://store.demoqa.com/";
    private final String PHONE_PAGE = "http://store.demoqa.com/?s=Search+Productsphone&post_type=wpsc-product";
    private final String LAPTOP_PAGE = "http://store.demoqa.com/products-page/product-category/macbooks/apple-13-inch-macbook-pro/";
    private final String emptyCart = "Oops, there is nothing in your cart.";
    private final String noProduct = "Sorry, but nothing matched your search criteria. Please try again with some different keywords.";
    
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
        String login = driver.findElement(By.className("entry-title")).getText();
        assertEquals("Your Account", login);
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
    //    String errorMsg1 = driver.findElement(By.xpath(".//*[@class='response']")).getText();
    //    String errorMsg1 = driver.findElement(By.className("response")).getText();
        String errorMsg1 = driver.findElement(By.xpath(".//*[@id='ajax_loginform']/p[1]/strong")).getText(); 
        assertEquals("ERROR", errorMsg1);     
    }
    
    //Scenario3: Failed to Login with Invalid Username
    @And("User enters Invalid Username")
    public void user_enters_invalid_username() {
        driver.findElement(By.id("log")).sendKeys("testhahaha"); 	 
	driver.findElement(By.id("login")).click();
    }
    
    @Then("Page displayed Login Error Message")
    public void page_displayed_login_error_message(){
        String errorMsg2 = driver.findElement(By.xpath(".//*[@id='ajax_loginform']/p[1]/strong")).getText();
        assertEquals("ERROR", errorMsg2);
    }
    
    //Feature: Search Products
    //Scenario1: Search an Available Product
    @When("User enter Product Name")
    public void user_enters_prodcut_name() throws Throwable{
        WebElement search = driver.findElement(By.className("search"));
        search.sendKeys("phone");
        search.sendKeys(Keys.ENTER);
        Thread.sleep(10000);
//        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
    
    @Then("Page displayed related products")
    public void page_displayed_related_product(){
        String phone = driver.getCurrentUrl();
        assertEquals(PHONE_PAGE, phone);
    }
    
    //Scenario2: Search a Nonexistent Product
    @When("User enter irrelevant product name")
    public void user_enter_irrelevant_product_name(){
        WebElement search = driver.findElement(By.className("searchform"));
        search.sendKeys("scarf");
        search.submit();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);    
    }
    
    @Then("Page displayed message no product matched")
    public void page_displayed_message_no_product_matched(){
        String p = driver.findElement(By.xpath(".//*[@id='content']/p")).getText();
        assertEquals(noProduct, p);
    }
    
    //Feature: Purchase Products
    //Scenario1: Add a product to cart
    @Given("User in the laptop page")
    public void laptop_page(){
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, 30);
        driver.get(LAPTOP_PAGE);     
    }
    
    @When("User add the laptop to the cart")
    public void add_the_laptop_to_the_cart(){
        driver.findElement(By.className("input-button-buy")).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
    
    @And("User navigate to cart page")
    public void user_navigate_to_cart_page() throws Throwable{
        driver.findElement(By.xpath(".//a[@class='go_to_checkout']")).click();
        Thread.sleep(10000);
    }
    
    @Then("User cart should display with 1 item")
    public void cart_count(){
        String quantity1 = driver.findElement(By.className("count")).getText();
        assertEquals("1", quantity1);
    }
    
    //Scenario2: Remove a Product   
    @And("click on the remove button")
    public void click_on_remove_button() throws Throwable{
        driver.findElement(By.cssSelector("input[value='Remove']")).click();
        Thread.sleep(10000);
    }
    
    @Then("the cart should be empty")
    public void empty_cart(){
        String empty = driver.findElement(By.className("entry-content")).getText();
        assertEquals(emptyCart, empty);
    }
    
    //Scenario3: Change Quantity of a Product 
    @And("User modify the quantity of product")
    public void change_quantity() throws Throwable{
        driver.findElement(By.xpath(".//*[@name='quantity']")).clear();
        driver.findElement(By.xpath(".//*[@name='quantity']")).sendKeys("2");
        driver.findElement(By.xpath(".//*[@value='Update']")).click();
        Thread.sleep(10000);
    }
    
    @Then("the quantity of product and the total price should be updated")
    public void quantity_and_price_updated(){
        String quantity2 = driver.findElement(By.xpath(".//*[@name='quantity']")).getAttribute("value");
        assertEquals("2", quantity2);
        String price = driver.findElement(By.className("pricedisplay")).getText();
        assertEquals("$1,728.00", price);
    }
    
    //Scenario4: Payment Page
    @And("User click on Continue Button")
    public void user_click_continue_button() throws Throwable{
        driver.findElement(By.className("step2")).click();
        Thread.sleep(10000);
    }
    
    @Then("the page should navigate to the Checkout information page")
    public void navigate_to_checkout_information_page(){
        String ship = driver.findElement(By.xpath(".//*[@class='wpsc_billing_forms ']/h4")).getText();
        assertEquals("Your billing/contact details", ship);
    }
    
    @After
    public void cleanUp(){
        driver.quit();
    }
}
