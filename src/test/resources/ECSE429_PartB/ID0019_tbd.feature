@scenarioOutline
Feature: Create category project relationship
  As a user,
  I want to create a relationship between a category and the project instance, to be more organised.

# /categories/:id/projects

  # Normal Flow
  @scenarioOutline
  Scenario Outline: See projects related to specific category
    Given a category with id "<id_cat>"
    When I create project with title "<title1>" for category with id "<id_cat>"
    When I create project with title "<title2>" for category with id "<id_cat>"
    Then verify project with title "<title1>" exists under category "<id_cat>"
    Then verify project with title "<title2>" exists under category "<id_cat>"
    Examples:
      | id_cat | title1    | title2  |
      | 1      | a title 1 |a title 2|