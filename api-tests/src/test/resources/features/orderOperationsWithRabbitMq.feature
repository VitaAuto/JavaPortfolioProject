@api
Feature: Order operations with RabbitMQ integration
  As an user
  I want to manage orders with RabbitMQ integration
  So that I can create, update, patch, and delete orders

  @smoke
  Scenario: Create new order with valid data and verify RabbitMQ integration
    Given user logs in with valid username and valid password
    When user creates new order with username "Tim", description "Create new order with RabbitMQ integration", and amount "901.99"
    Then response should have status code 201
    And RabbitMQ message should contain specific Correlation id and following body fields:
      | username    | Tim                                        |
      | description | Create new order with RabbitMQ integration |
      | amount      | 901.99                                     |
    And RabbitMQ queue is empty after consuming message