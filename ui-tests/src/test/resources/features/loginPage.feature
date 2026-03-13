@ui
Feature: Login
  As an user
  I want to login
  So that I can perform additional actions

  Scenario: User with valid credentials can log in
    Given user navigates to "Login" page
    When user logs in as "standard"
    Then "Main" page is open

  Scenario: Mandatory fields placeholders validation
    Given user navigates to "Login" page
    Then login field placeholder is "Username"
    Then password field placeholder is "Password"
    When user focuses on login field
    Then login field placeholder is "Username"
    When user enters "testuser" as login
    Then login field value is "testuser"
    When user focuses on password field
    Then password field placeholder is "Password"
    When user enters "secret" as password
    Then password field is masked

  Scenario Outline: Mandatory fields validation
    Given user navigates to "Login" page
    And user tries to log in
    Then "Login" page error message contains "Username is required"
    When user enters "<login>" as login
    And user tries to log in
    Then "Login" page error message contains "Password is required"
    When user enters "<password>" as password
    And user tries to log in
    Then "Login" page error message contains "Username and password do not match any user in this service"

    Examples:
    |login  |password |
    |L      |P        |
    |12309  |01233435 |
    |!.&    |@^)      |

  Scenario: Locked user can not log in
    Given user navigates to "Login" page
    When user logs in as "locked_out"
    Then "Login" page error message contains "Sorry, this user has been locked out."
