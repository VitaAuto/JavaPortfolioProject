@ui
Feature: Main page properties
  As an user
  I want to perform actions on Main page

  @smoke
  Scenario: Definite number of items is shown on the Main page
    Given user navigates to "Login" page
    When user logs in as "standard"
    Then "Main" page is open
    And 6 items are shown