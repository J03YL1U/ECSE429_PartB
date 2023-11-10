@scenarioOutline
Feature: Edit task description
  As a user,
  I want to be able to edit my task’s description to better describe what needs to be done.

  # Normal Flow
  @scenarioOutline
  Scenario Outline: Change description of a current task to a new one
    Given a todo task with description "<current_description>"
    When I update the todo task description to "<new_description>"
    Then the todo task will have the description "<resulting_description>"
    Examples:
      | current_description                   | new_description                           | resulting_description               |
      | Revise Software Validation Chapter 1  | Revise Software Delivery Chapter 3        | Revise Software Delivery Chapter 3  |
      | Practice Weekly Exercises             | Go to Office Hours                        | Go to Office Hours                  |

  # Alternate Flow (updating description with the same description)
  Scenario Outline: Change description of a current task to the same description
    Given a todo task with description "<current_description>"
    When I update the todo task description to "<new_description>"
    Then the todo task will have the description "<resulting_description>"
    Examples:
      | current_description                   | new_description                       | resulting_description                |
      | Revise Software Validation Chapter 1  | Revise Software Validation Chapter 1  | Revise Software Validation Chapter 1 |
      | Practice Weekly Exercises             | Practice Weekly Exercises             | Practice Weekly Exercises            |

  # Error Flow
  Scenario Outline: Change description of an invalid todo task
    Given a todo task with description "<current_description>"
    When I update the todo task with "<invalid_id>" with description "<new_description>"
    Then the expected status code is "<status_code>"
    Then the todo task will have the description "<current_description>"
    Examples:
      | current_description                       | invalid_id        | new_description                      | status_code |
      | Revise Software Validation Chapter 1      | 100               | Revise Software Delivery Chapter 3   | 404         |
      | Practice Weekly Exercises                 | 200               | Go to Office Hours                   | 404         |