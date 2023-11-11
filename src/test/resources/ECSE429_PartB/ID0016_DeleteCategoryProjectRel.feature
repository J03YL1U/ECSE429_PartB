@scenarioOutline
Feature: Delete Todo Category Relationship
  As a user,
  I want to delete a category to project relationship, so that I can reorganise my todos.

  #/categories/:id/projects/:id

  # Normal Flow
  @scenarioOutline
  Scenario Outline: Delete Categories and Projects Rel
    Given a category with id "<id_cat>"
    When I create project with title "<title>" for category with id "<id_cat>"
    Then I delete category "<id_cat>" and project "<id_proj>" relationship
    Then there is no project for category "<id_cat>"
    Examples:
      | id_cat    | title         | id_proj |
      | 1         | homework 55   |  2      |


   # Alternate Flow (no title for project)
  @scenarioOutline
  Scenario Outline: Delete Categories and Projects Rel - Empty title
    Given a category with id "<id_cat>"
    When I create project with title "<title>" for category with id "<id_cat>"
    Then I delete category "<id_cat>" and project "<id_proj>" relationship
    Then there is no project for category "<id_cat>"
    Examples:
      | id_cat    | title | id_proj |
      | 1         |       |  2      |



