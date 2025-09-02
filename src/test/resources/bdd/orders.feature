Feature: Process order through gateway
  As an e-commerce application
  I want to send orders to Sunny gateway
  So that payment processing can be handled

  Scenario: Valid order processed successfully
    Given the application sends a complete valid order
    When the application calls POST /api/orders
    Then the order should be processed
    And respond with status 201
    And return order_id and payment_links

  Scenario: Invalid order data - missing required fields
    Given the application sends incomplete order data
    When the application calls POST /api/orders
    Then respond with status 400
    And return validation errors

  Scenario: Invalid order data - negative amount
    Given the application sends an order with negative amount
    When the application calls POST /api/orders
    Then respond with status 400
    And return validation error "Amount must be positive"

  Scenario: Provider unavailable
    Given the application sends a valid order
    And all payment providers are unavailable
    When the application calls POST /api/orders
    Then respond with status 503
    And return provider error message

  Scenario: Order with multiple items
    Given the application sends an order with multiple items
    When the application calls POST /api/orders
    Then the order should be processed
    And respond with status 201
    And return order_id with correct total amount