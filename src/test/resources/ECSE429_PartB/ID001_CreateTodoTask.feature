@scenarioOutline
Feature: Create todo
  As a user,
  I want to be able to add new tasks,
  to know what I need to do

  # Normal Flow
  @scenarioOutline
  Scenario Outline: Create a new todo task
    Given a list of todo task "<current_todo_tasks>"
    When I create the todo task "<new_todo_task>"
    Then the expected todo status code received from the system is "<statusCode>"
    Then the number of todo tasks in the system will be "<number_of_tasks>"
    Then "<new_todo_task>" will be in the list of todo tasks
    Examples:
      | current_todo_tasks   | new_todo_task         | statusCode   | number_of_tasks    |
      | Do homework          | Do chores             | 200          | 4                  |
      | Do classwork         | Make dinner           | 200          | 4                  |

  # Alternate Flow: Creating a todo task with the same title as an existing one
  @scenarioOutline
  Scenario Outline: Create a new todo task with same title as an existing one
    Given a list of todo task "<current_todo_tasks>"
    When I create the todo task "<new_todo_task>"
    Then the expected todo status code received from the system is "<statusCode>"
    Then the number of todo tasks in the system will be "<number_of_tasks>"
    Then "<new_todo_task>" will be in the list of todo tasks
    Examples:
      | current_todo_tasks   | new_todo_task        | statusCode   | number_of_tasks    |
      | Do homework          | Do homework          | 200          | 4                  |
      | Do classwork         | Do classwork         | 200          | 4                  |


  # Error Flow
  @scenarioOutline
  Scenario Outline: Create a new todo_task with empty title
    Given a list of todo task "<current_todo_tasks>"
    When I create the todo task "<added_todo_task>"
    Then the expected todo status code received from the system is "<statusCode>"
    Examples:
      | current_todo_tasks   | added_todo_task       | statusCode  |
      | Home                 |                       | 404         |
      | School               |                       | 404         |