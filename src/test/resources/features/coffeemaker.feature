Feature: Make Coffee
  Any customer can buy a coffee if the recipe is valid and have enough money. Ingredient in the inventory should be used.

  Background: buy the coffee.
    Given Coffee maker is ready to serve.

  Scenario: buy the invalid coffee.
    When The customer order a invalid coffee recipe.
    Then The customer order recipe number: 0 and money: 100 then the change equal to 100.

  Scenario: buy with not enough money.
    When The customer order coffee with not enough money.
    Then The customer order recipe number: 1 and money: 2 then the change equal to 2.

  Scenario: buy coffee with exact money.
    When The customer order coffee with exact money.
    Then The customer order recipe number: 2 and money: 50 then the change equal to 50.
