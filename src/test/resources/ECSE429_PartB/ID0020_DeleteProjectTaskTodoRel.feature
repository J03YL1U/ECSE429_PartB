@scenarioOutline
Feature: Edit task description
  As a user,
  I want to delete a relationship named Tasks between a a project and todo, to better represent the relationships of todos.

 #/projects/:id/tasks/:id

  @scenarioOutline
  Scenario Outline: See projects related to specific category
    Given a todo with id "<id_todo>"
    Given a project with id "<id_proj>"
    When I delete relationship between project "<id_proj>" and todo "<id_todo>"
    Then deleted relationship for project "<id_proj>" and todo does not exist
    Examples:
      | id_todo |  id_proj   |
      |    1    |    1       |
