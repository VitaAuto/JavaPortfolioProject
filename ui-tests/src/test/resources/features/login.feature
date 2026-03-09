Feature: UI management
  As an user
  I want to manage orders
  So that I can view, create, update, and delete orders

  Scenario: Valid user can log in
    Given user navigates to "Login" page
    When user logs in as "standard"
    Then "Main" page is open

  Scenario: Invalid user can not log in
    Given user navigates to "Login" page
    When user logs in as "locked_out"
    Then "Login" page error message contains "Sorry, this user has been locked out."
