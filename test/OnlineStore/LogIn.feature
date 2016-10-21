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
