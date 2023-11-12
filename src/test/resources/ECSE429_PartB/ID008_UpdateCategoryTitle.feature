Feature: Update a category's title
  As a user,
  I want to be able to update a category’s title,
  so that it is more fitting.

  #Normal Flow
  @scenarioOutline
  Scenario Outline: Update an existing category with a new title
    Given an existing category with title "<existing_title>"
    When I update the existing title to "<new_title>"
    Then the expected category status code received from the system is "<statusCode>"
    And the existing category's title should be "<new_title>"
    Examples:
      | existing_title | new_title  | statusCode |
      | pottery        | clay stuff | 200        |

  #Alternate flow
  @scenarioOutline
  Scenario Outline: Update an existing category with the same title
    Given an existing category with title "<existing_title>"
    When I update the existing title to "<new_title>"
    Then the expected category status code received from the system is "<statusCode>"
    And the existing category's title should be "<new_title>"
    Examples:
      | existing_title | new_title | statusCode |
      | pottery        | pottery   | 200        |

  #Error flow
  @scenarioOutline
  Scenario Outline: Update a non existing category with a new category
    Given an existing category with title "<existing_title>"
    When I try to update the title to "<new_title>" of a category that doesn't exist
    Then the expected category status code received from the system is "<statusCode>"
    Examples:
      | existing_title | new_title        | statusCode |
      | pottery        | does this exist? | 404        |