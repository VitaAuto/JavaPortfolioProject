@ui
Feature: Cart page properties
  As an user
  I want to perform various actions from Cart page

  @smoke
  Scenario: Cart page order correctness after adding a product to the cart from the Main page
    Given user navigates to "Login" page
    When user logs in as "standard"
    Then "Main" page is open
    When user remembers current quantity of shown items in the cart
    And user adds "Sauce Labs Backpack" to cart
    And user adds "Sauce Labs Bike Light" to cart
    Then quantity of shown items in the cart is increased by 2
    When user remembers current quantity of shown items in the cart
    And user clicks "Cart button"
    Then "Cart" page is open
    Then quantity of shown items in the cart is 2
    And the following products are shown in order:
      | position | name                             | price   |
      | 1        | Sauce Labs Backpack              | $29.99  |
      | 2        | Sauce Labs Bike Light            | $9.99   |
    And "Sauce Labs Backpack" product should have button with text "Remove"
    And "Sauce Labs Bike Light" product should have button with text "Remove"