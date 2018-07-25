Feature: Allow changes to in memory database of orders

  Scenario: Server gets a request to reset one order
    Given the server is running
    When the server receives a reset request with order id
      | order3 |
    Then the order state and associated file state are set to READY
      | order3 | READY |

  Scenario: Server gets a request to reset all orders
    Given the server is running
    When the server receives a reset all request
    Then all the order state and file state is set to READY

  Scenario: Server gets a request to update update a file state
    Given the server is running
    Given there are a list of orders with READY state with files in a READY state
      | order3 | fileScan1.txt | READY |
    When a request to update a file to DONE
    Then the result should update the file state to DONE
      | order3 | fileScan1.txt | DONE |
    And any state changes logged at info level

  Scenario: Server gets a request for all READY orders
    Given the server is running
    Given there are a list of orders with READY state with files in a READY state
      | order3 | fileScan1.txt | READY |
    When a request for all READY orders with start 2 and returns 10 items
    Then the result should return item 2 to 4
      | order4 | order1 | order2 |
    And any state changes logged at info level