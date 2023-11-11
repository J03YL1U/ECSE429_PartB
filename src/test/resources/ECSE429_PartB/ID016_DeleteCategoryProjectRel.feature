@scenarioOutline
Feature: Delete Todo Category Relationship
  As a user,
  I want to delete a category to project relationship, so that I can reorganise my todos.

  # Normal Flow
  @scenarioOutline
  Scenario Outline: Delete Categories and Projects Rel
    Given a category with id "<id_cat>"
    When I create project with title "<title>" for category with id "<id_cat>" with status "<status1>"
    Then I delete category "<id_cat>" and project "<id_proj>" relationship with status "<status2>"
    Then there is no project for category "<id_cat>"
    Examples:
      | id_cat    | title              | id_proj |  status1  | status2  |
      | 1         | homework 55        |  2      |  201      |  200     |
      | 1         | some other title   |  2      |  201      |  200     |


  # Alternate Flow (no title for project)
  @scenarioOutline
  Scenario Outline: Delete Categories and Projects Rel - Empty title
    Given a category with id "<id_cat>"
    When I create project with title "<title>" for category with id "<id_cat>" with status "<status1>"
    Then I delete category "<id_cat>" and project "<id_proj>" relationship with status "<status2>"
    Then there is no project for category "<id_cat>"
    Examples:
      | id_cat    | title | id_proj |  status1  | status2  |
      | 1         |       |  2      |  201      |  200     |


  # Error Flow (category and project relationship does not exist)
  @scenarioOutline
  Scenario Outline: Delete Non-Existing Categories and Projects Rel
    Then I delete category "<id_cat>" and project "<id_proj>" relationship with status "<status>"
    Examples:
      | id_cat    | id_proj | status|
      | 3         | 3       | 404   |
      | 200       | 5       | 404   |




