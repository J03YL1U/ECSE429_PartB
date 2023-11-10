@scenarioOutline
Feature: Changing Todo Task Done Status
  As a user,
  I want to be able to change the status of my work to know whether they are done or not.

  # Normal Flow
  @scenarioOutline
  Scenario Outline: Change the done status of a task to a new one
    Given a task with done status "<current_done_status>"
    When I update the task done status to "<new_done_status>"
    Then the task has done status "<resulting_done_status>"
    Examples:
      | current_done_status          | new_done_status           | resulting_done_status |
      | true                         | false                     | false                 |
      | false                        | true                      | true                  |

  # Alternate Flow (updating done status with the same done status value)
  Scenario Outline: Change done status of a current task to the same status
    Given a task with done status "<current_done_status>"
    When I update the task done status to "<new_done_status>"
    Then the task has done status "<resulting_done_status>"
    Examples:
      | current_done_status          | new_done_status              | resulting_done_status       |
      | true                         | true                         | true                        |
      | false                        | false                        | false                       |

  # Error Flow
  Scenario Outline: Change done status of a non existent task
    Given a task with done status "<current_done_status>"
    When I update the todo task with "<invalid_id>" to done status "<new_done_status>"
    Then the expected status code is "<status_code>"
    Then the task has done status "<current_done_status>"
    Examples:
      | current_done_status      | invalid_id             | new_done_status        | status_code |
      | false                    | 100                    | true                   | 404         |
      | true                     | 100                    | false                  | 404         |