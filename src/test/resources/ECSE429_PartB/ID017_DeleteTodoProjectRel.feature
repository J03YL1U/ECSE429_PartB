@scenarioOutline
Feature: Delete todo project Relationship
  As a user,
  I want to delete a todo’s project relationship, so that I can better keep track of a todo's status.

  # Normal Flow
  @scenarioOutline
  Scenario Outline: Delete todo and projects rel
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
  Scenario Outline: Delete todo and projects rel - bad ID
    Given a project with id "<id_proj>"
    When I delete relationship between todo "<id_todo>" and project "<id_proj>" with status "<status>"
    Examples:
      | id_todo |  id_proj   | status |
      |    #@$  |    1       |  404   |

  # Error Flow - Project does not exist
  @scenarioOutline
  Scenario Outline: Delete todo and projects rel - non-existing project
    Given a project with id "<id_proj>"
    When I delete relationship between todo "<id_todo>" and project "<id_proj>" with status "<status>"
    Examples:
      | id_todo |  id_proj   | status |
      |    3    |    1       |  400   |






