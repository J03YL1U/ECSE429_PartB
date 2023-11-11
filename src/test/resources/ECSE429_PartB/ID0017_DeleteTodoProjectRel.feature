@scenarioOutline
Feature: Delete Todo Project Relationship
  As a user,
  I want to delete a todo’s attached project, so that I can reorganise my todos.

  # Normal Flow
  @scenarioOutline
  Scenario Outline: Delete Todo and Projects Rel
    Given a todo with id "<id_todo>"
    Given a project with id "<id_proj>"
    When I delete relationship between todo "<id_todo>" and project "<id_proj>" with status "<status>"
    Then deleted relationship for todo "<id_todo>" and project does not exist
    Examples:
      | id_todo |  id_proj   | status |
      |    1    |    1       |  200   |
      |    2    |    1       |  200   |

  # Alternate Flow (Malformed request despite being a string)
  @scenarioOutline
  Scenario Outline: Delete Todo and Projects Rel - Bad ID
    Given a project with id "<id_proj>"
    When I delete relationship between todo "<id_todo>" and project "<id_proj>" with status "<status>"
    Examples:
      | id_todo |  id_proj   | status |
      |    #@$  |    1       |  404   |

  # Error Flow - Project does not exist
  @scenarioOutline
  Scenario Outline: Delete Todo and Projects Rel - Non-existing project
    Given a project with id "<id_proj>"
    When I delete relationship between todo "<id_todo>" and project "<id_proj>" with status "<status>"
    Examples:
      | id_todo |  id_proj   | status |
      |    3    |    1       |  400   |






