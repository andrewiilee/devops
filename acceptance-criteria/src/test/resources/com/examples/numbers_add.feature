Feature: Add numbers
  Be a calculator that adds numbers

  Scenario: Result of adding two numbers
    Given a number 5 with another number 6
    When the calculator wants to add them together
    Then the result is 11

  Scenario: Result of adding two numbers
    Given a number 2 with another number 1
    When the calculator wants to add them together
    Then the result is 3

  Scenario: Result of adding two numbers
    Given a number 1 with another number 1
    When the calculator wants to add them together
    Then the result is 2