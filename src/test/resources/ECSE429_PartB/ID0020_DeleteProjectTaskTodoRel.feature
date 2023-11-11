@scenarioOutline
Feature: Delete task relationship between project and todo
  As a user,
  I want to delete a relationship between a project and todo (named tasks), to better represent the relationships of todos.

  # Normal Flow
  @scenarioOutline
  Scenario Outline: Delete task relationship between project and todo
    Given a todo with id "<id_todo>"
    Given a project with id "<id_proj>"
    When I delete relationship between project "<id_proj>" and todo "<id_todo>" with status "<status>"
    Then deleted relationship for project "<id_proj>" and todo does not exist
    Examples:
      | id_todo |  id_proj   | status |
      |    1    |    1       |  200   |

  # Alternate flow (Todo exists but no relationship)
  @scenarioOutline
  Scenario Outline: Delete task relationship between project and todo - Existing todo but no relationship
    When I create the todo task "<new_todo_task>"
    Given a project with id "<id_proj>"
    When I delete relationship between project "<id_proj>" and todo "<id_todo>" with status "<status>"

    Examples:
      | new_todo_task | id_todo |  id_proj   | status |
      |  hi world     |  3      |    1        |  404   |

  # Error Flow
  @scenarioOutline
  Scenario Outline: Delete task relationship between project and todo - Invalid ID
    When I delete relationship between project "<id_proj>" and todo "<id_todo>" with status "<status>"
    Examples:
      | id_todo |  id_proj   | status |
      |  429    |    1       |  404   |

