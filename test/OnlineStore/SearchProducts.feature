Feature: Search Products

        Scenario: Search an Available Product
                Given User is on Home Page
                When User enter Product Name
                Then Page displayed related products

        Scenario: Search a Nonexistent Product
                Given User is on Home Page
                When User enter irrelevant product name
                Then Page displayed message no product matched

