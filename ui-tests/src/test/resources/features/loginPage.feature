@ui
Feature: Login
  As an user
  I want to login
  So that I can perform additional actions

  @smoke
  Scenario: User with valid credentials can log in
    Given user navigates to "Login" page
    When user logs in as "standard"
    Then "Main" page is open

  Scenario: Mandatory fields placeholders validation
    Given user navigates to "Login" page
    Then "Username field" placeholder is "Username"
    Then "Password field" placeholder is "Password"
    When user focuses on "Username field"
    Then "Username field" placeholder is "Username"
    When user enters "testuser" into "Username field"
    Then "Username field" value is "testuser"
    When user focuses on "Password field"
    Then "Password field" placeholder is "Password"
    When user enters "secret" into "Password field"
    Then "Password field" is masked

  @smoke
  Scenario Outline: Mandatory fields validation
    Given user navigates to "Login" page
    And user clicks "Login button"
    Then "Login error" message contains "Username is required"
    When user enters "<login>" into "Username field"
    And user clicks "Login button"
    Then "Login error" message contains "Password is required"
    When user enters "<password>" into "Password field"
    And user clicks "Login button"
    Then "Login error" message contains "Username and password do not match any user in this service"

    Examples:
    |login  |password |
    |L      |P        |
    |12309  |01233435 |
    |!.&    |@^)      |


    Scenario: Locked user can not log in
    Given user navigates to "Login" page
    When user logs in as "locked_out"
    Then "Login error" message contains "Sorry, this user has been locked out."
