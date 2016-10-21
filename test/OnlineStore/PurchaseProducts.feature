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
