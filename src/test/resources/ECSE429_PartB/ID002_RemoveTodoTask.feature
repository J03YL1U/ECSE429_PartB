@scenarioOutline
Feature: Remove a todo task
  As a user,
  I want to remove a todo task
  so that I no longer have an unneeded todo task that I've finished


  # Normal Flow
  @scenarioOutline
  Scenario Outline: Remove a todo task from the system
    Given a todo task with title "<todo_task>"
    When I remove the todo task with title "<todo_task>"
    Then the remove todo status code we should receive is "<statusCode>"
    Then the todo task with title "<todo_task>" will be removed from the system
    Examples:
      | todo_task         |statusCode |
      | Do Homework       | 200       |
      | Do Classwork      | 200       |

# Alternate Flow (removing todo task with the same titles)
  Scenario Outline: Remove todo task with same title
    Given two todo tasks with the same title "<todo_task>"
    When I remove the todo task with id "3"
    Then the remove todo status code we should receive is "<statusCode>"
    Then the todo task with id "3" will be removed from the system
    Examples:
      | todo_task           | statusCode |
      | Do Homework         | 200        |
      | Do Classwork        | 200        |

  # Error Flow
  Scenario Outline: Remove a todo task with invalid id
    Given a todo task with title "<todo_task>"
    When I remove the todo task with id "<ID>"
    Then the remove todo status code we should receive is "<statusCode>"
    Then "<todo_task>" will be in the list of todo tasks
    Examples:
      | todo_task           | ID   | statusCode   |
      | Do homework         | 100  | 404          |
      | Do classwork        | 100  | 404          |