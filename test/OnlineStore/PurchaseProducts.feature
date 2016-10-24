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
