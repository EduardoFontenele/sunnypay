Feature: Process order through gateway
  As an e-commerce application
  I want to send orders to Sunny gateway
  So that payment processing can be handled

  Scenario: A Pix operation is processed successfully
    Given a customer with validated data
    And the selected payment method is "PIX"
    When the order is submited
    Then the order should be persisted with order status "PENDING"
    And should return data for PIX payment
    And should trigger an order created event