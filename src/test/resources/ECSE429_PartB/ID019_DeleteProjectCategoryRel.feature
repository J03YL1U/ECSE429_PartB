@scenarioOutline
Feature: Delete project to category relationship
  As a user,
  I want to delete a relationship (named category) for a project, to be more organised.

  # Normal Flow
  Scenario Outline: Delete categories and projects rel
    When I create relationship categories with title "<title>" for project with id "<id_proj>"
    Then I delete project "<id_proj>" relationship to category "<id_cat>" relationship with status "<status>"
    Then there is no category for project "<id_proj>"
    Examples:
      | id_cat    | title         | id_proj | status |
      | 3         | homework 55   |  1      | 200    |

  # Alternate Flow (Special character title)
  Scenario Outline: Delete categories and projects rel - Special title
    When I create relationship categories with title "<title>" for project with id "<id_proj>"
    Then I delete project "<id_proj>" relationship to category "<id_cat>" relationship with status "<status>"
    Then there is no category for project "<id_proj>"
    Examples:
      | id_cat    | title         | id_proj | status |
      | 3         |   !@#$%^&*(   |  1      | 200    |

  # Error Flow
  Scenario Outline: Delete categories and projects rel - Category does not exist
    Then I delete project "<id_proj>" relationship to category "<id_cat>" relationship with status "<status>"
    Examples:
      | id_cat    | id_proj | status |
      | 4         |  1      | 404    |