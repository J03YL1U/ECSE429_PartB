Feature: Create a category
  As an user,
  I want to be able to create new categories,
  that I can organise my todos and projects.

  #Normal Flow
  @scenarioOutline
  Scenario Outline: Create a new category with a new title
    Given a list of categories "<current_categories>"
    When I create the category "<added_category>"
    Then the expected category status code received from the system is "<statusCode>"
    And the number of category in the system will be "<number_of_categories>"
    And "<added_category>" category will be in the list of category
    Examples:
      | current_categories | added_category | statusCode | number_of_categories |
      | Home               | Pottery        | 200        | 4                    |

  #Alternate flow
  @scenarioOutline
  Scenario Outline: Create a new category with an existing title
    Given a list of categories "<current_categories>"
    When I create the category "<existing_title_category>"
    Then the expected category status code received from the system is "<statusCode>"
    And the number of category in the system will be "<number_of_categories>"
    And "<existing_title_category>" category will be in the list of category
    Examples:
      | current_categories | existing_title_category | statusCode | number_of_categories |
      | Home               | Home                    | 200        | 4                    |

  #Error flow
  @scenarioOutline
  Scenario Outline: Create a new category with an existing title
    Given a list of categories "<current_categories>"
    When I create the category "<no_title_category>"
    Then the expected category status code received from the system is "<statusCode>"
    And the number of category in the system will be "<number_of_categories>"
#    And an error message "<error_message>" should be returned
    Examples:
      | current_categories | no_title_category | statusCode | number_of_categories | error_message    |
      | Home               |                   | 404        | 3                    | failed something |