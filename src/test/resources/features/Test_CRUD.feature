@wip
Feature: Test Crud Method in Fruit Shop API Testing

  Scenario: Add a product to a Vendor
    Given the vendor exist
    When I send a valid create product payload
    Then response status code should be 201
    And create product response should be valid

  Scenario: Update the product
    Given  the product ID was created
    When send updated name and the price of the product
    Then response status code should be 200
    And updated product response should be valid


Scenario: Delete the product
  Given the product ID was created
  When Delete the created product
  Then response status code should be 200


