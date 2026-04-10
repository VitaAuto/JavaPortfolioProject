@ui
Feature: Main page properties
  As an user
  I want to perform various actions from Main page

  @smoke
  Scenario: Definite number of products is shown on the Main page
    Given user navigates to "Login" page
    When user logs in as "standard"
    Then "Main" page is open
    And 6 items are shown

  @smoke
  Scenario: User can log out from Main page
    Given user navigates to "Login" page
    When user logs in as "standard"
    Then "Main" page is open

  @smoke
  Scenario: Specific products and order on the Main page
    Given user navigates to "Login" page
    When user logs in as "standard"
    Then "Main" page is open
    And the following products are shown in order:
      | position | name                             | price   |
      | 1        | Sauce Labs Backpack              | $29.99  |
      | 2        | Sauce Labs Bike Light            | $9.99   |
      | 3        | Sauce Labs Bolt T-Shirt          | $15.99  |
      | 4        | Sauce Labs Fleece Jacket         | $49.99  |
      | 5        | Sauce Labs Onesie                | $7.99   |
      | 6        | Test.allTheThings() T-Shirt (Red)| $15.99  |