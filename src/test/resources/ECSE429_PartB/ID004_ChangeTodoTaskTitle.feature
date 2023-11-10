@scenarioOutline
Feature: Changing task title
  As a user,
  I want to change a task's title,
  to better represent the work that I have to do.

  # Normal Flow
  @scenarioOutline
  Scenario Outline: Change title of a current task to a new one
    Given a todo task with title "<current_title>"
    When I update the task title to "<new_title>"
    Then the todo task will have title "<resulting_title>"
    Examples:
      | current_title                | new_title                 | resulting_title            |
      | Start project report         | Complete project report   | Complete project report    |
      | Revise for Midterm           | Practice for Midterm      | Practice for Midterm       |

  # Alternate Flow (updating title with the same title)
  Scenario Outline: Change title of a current task to the same title
    Given a todo task with title "<current_title>"
    When I update the task title to "<new_title>"
    Then the todo task will have title "<resulting_title>"
    Examples:
      | current_title                 | new_title                      | resulting_title       |
      | Start project report          | Start project report           | Start project report  |
      | Revise for Midterm            | Revise for Midterm             | Revise for Midterm    |

  # Error Flow
  Scenario Outline: Change title of a todo task with invalid id
    Given a todo task with title "<current_title>"
    When I update the todo task "<invalid_id>" with title "<new_title>"
    Then the expected status code is "<status_code>"
    Then the todo task will have title "<current_title>"
    Examples:
      | current_title            | invalid_id    | new_title                 | status_code |
      | Start project report     | 100           | Complete project report   | 404         |
      | Revise for Midterm       | 100           | Practice for Midterm      | 404         |