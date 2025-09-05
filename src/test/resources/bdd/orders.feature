Feature: Process order through gateway
  As an e-commerce application
  I want to send orders to Sunny gateway
  So that payment processing can be handled

  Scenario: Invalid order data - missing required fields
    Given the application sends incomplete order data
    When the application calls method "POST" with URI "/api/orders"
    Then should respond with status 400
    And should return error response with validation errors
