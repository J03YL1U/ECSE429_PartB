Feature: Delete a category
  As a user,
  I want to be able to delete a category from the system,
  so that useless categories are removed.

  #Normal Flow
  @scenarioOutline
  Scenario Outline: Delete an existing category
    Given a list of categories "<current_categories>"
    When I delete a category "<deleted_category>"
    Then the expected category status code received from the system is "<statusCode>"
    And the number of category in the system will be "<number_of_categories>"
    And "<deleted_category>" category will no longer exist in the system
    Examples:
      | current_categories | deleted_category | statusCode | number_of_categories |
      | Pottery            | Pottery          | 200        | 2                    |

  #Alternate flow
  @scenarioOutline
  Scenario Outline: Delete an existing category that has the same title as another
    Given a list of categories "<current_categories>" with two categories of the title
    When I delete a category "<deleted_category>"
    Then the expected category status code received from the system is "<statusCode>"
    And the number of category in the system will be "<number_of_categories>"
    And "<deleted_category>" category will no longer exist in the system
    Examples:
      | current_categories | deleted_category | statusCode | number_of_categories |
      | Pottery            | Pottery          | 200        | 3                    |

  #Error flow
  @scenarioOutline
  Scenario Outline: Delete a category that doesn't exist
    Given a list of categories "<current_categories>"
    When I delete a category "<deleted_category>" that is not part of the list
    Then the expected category status code received from the system is "<statusCode>"
    And the number of category in the system will be "<number_of_categories>"
    Examples:
      | current_categories | deleted_category | statusCode | number_of_categories |
      | Pottery            | ECSE 429         | 404        | 3                    |