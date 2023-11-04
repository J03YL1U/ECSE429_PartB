@scenarioOutline
Feature: Create todo
  As a user,
  I want to be able to add new tasks,
  to know what I need to do

  # Normal Flow
  @scenarioOutline
  Scenario Outline: Creating a new todo task
    Given a list of todo task "<current_todo_tasks>"
    When I create the todo task "<added_todo_task>"
    Then the expected todo status code received from the system is "<statusCode>"
    Then the number of todo tasks in the system will be "<number_of_tasks>"
    Then "<added_todo_task>" todo will be in the list of todo tasks
    Examples:
      | current_todo_tasks   | added_todo_task       | statusCode   | number_of_tasks    |
      | Home                 | Office work           | 200          | 4                  |
#      | School               | Library               | 200          | 4                  |

  # Alternate Flow (Creating a category with the same title as an existing one)
#  Scenario Outline: Creating a new todo task with existing title
#    Given a todo task list "<current_todo_tasks>"
#    When I create the todo task "<added_todo_task>"
#    Then the returned todo status code of the system is "<statusCode>"
#    And "<added_todo_task>" todo will be in the list "<resulting_todo_tasks>"
#    Examples:
#      | current_todo_tasks   | added_todo_task       | statusCode   |resulting_todo_tasks               |
#      | Drive to library     | Drive to library      | 200          |Drive to library,Drive to library |
#      | School               | Library               | 200          |School,Library      |
#
#  # Error Flow
#  Scenario Outline: Creating a new todo_task with empty title
#    Given a todo task list "<current_todo_tasks>"
#    When I create the todo task "<added_todo_task>"
#    Then the returned todo status code of the system is "<statusCode>"
#    And the todo error message "<error_message>" is displayed
#    Examples:
#      | current_todo_task    | added_todo_task       | statusCode  | error_message                                   |
#      | Home                 |                       | 404         | Failed Validation: title : can not be empty     |
#      | School               |                       | 404         | Failed Validation: title : can not be empty     |