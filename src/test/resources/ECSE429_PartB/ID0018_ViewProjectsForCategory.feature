@scenarioOutline
Feature: Get projects from category
  As a user,
  As a user, I want to see the project items related to a specific category, so that I can keep track of a category and its project

  #/categories/:id/projects

  # Normal Flow
  @scenarioOutline
  Scenario Outline: See projects related to specific category
    Given a category with id "<id_cat>"
    When I create project with title "<title>" for category with id "<id_cat>"
    Then verify project with title "<title>" exists under category "<id_cat>"
    Examples:
     | id_cat | title  |
     | 1      | a title |