Feature: Add numbers
  Be a calculator that adds numbers

  Scenario: Result of adding 5 and 6
    Given a number 5 with another number 6
    When the calculator wants to add them together
    Then the result is 11

  Scenario: Result of adding 2 and 1
    Given a number 2 with another number 1
    When the calculator wants to add them together
    Then the result is 3

  Scenario: Result of adding 1 and 1
    Given a number 1 with another number 1
    When the calculator wants to add them together
    Then the result is 2

  Scenario: Result of adding 16 and 14
    Given a some number with some another number
      | 16 | 14 |
    When the calculator wants to add them together
    Then the result is added
      | 30 |

  Scenario Outline: Result of adding a data table
    Given a number <one> with another number <two>
    When the calculator wants to add them together
    Then the result is <result>
    Examples:
      | one | two | result |
      | 9   | 9   | 18     |
      | 13  | 24  | 37     |
      | 14  | 0   | 14     |


