@scenarioOutline
Feature: Delete Todo Project Relationship
  As a user,
  I want to delete a todo’s attached project, so that I can reorganise my todos.

  #/todos/:id/tasksof/:id

  # Normal Flow
  @scenarioOutline
  Scenario Outline: Delete Todo and Projects Rel
    Given a todo with id "<id_todo>"
    Given a project with id "<id_proj>"
    When I delete relationship between todo "<id_todo>" and project "<id_proj>"
    Then deleted relationship for todo "<id_todo>" and project does not exist
    Examples:
      | id_todo |  id_proj   |
      |    1    |    1       |




