@scenarioOutline
Feature: Get projects from category
  As a user,
  As a user, I want to see the project items related to a specific category, so that I can keep track of a category and its pSrojects.

  # Normal Flow
  @scenarioOutline
  Scenario Outline: See projects related to specific category
    Given a category with id "<id_cat>"
    When I create project with title "<title>" for category with id "<id_cat>" with status "<status>"
    Then verify project with title "<title>" exists under category "<id_cat>"
    Examples:
     | id_cat | title        | status |
     | 1      | a title      |  201   |
     | 2      | some title 2 |  201   |

  # Alternate Flow (Special characters title)
  @scenarioOutline
  Scenario Outline: See projects related to specific category - special title
    Given a category with id "<id_cat>"
    When I create project with title "<title>" for category with id "<id_cat>" with status "<status>"
    Then verify project with title "<title>" exists under category "<id_cat>"
    Examples:
      | id_cat | title                           | status |
      | 1      |  #$FSF&*(!*@#&*(!&@$            |  201   |

  # Error Flow - No category with id
  @scenarioOutline
  Scenario Outline: See projects related to specific category - non-existing category
    When I create project with title "<title>" for category with id "<id_cat>" with status "<status>"
    Examples:
      | id_cat | title        | status |
      | 429    |  a title     |  404   |
