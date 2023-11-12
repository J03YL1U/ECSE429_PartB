Feature: Update a category's description
  As an user,
  I want to be able to update a category’s description,
  so that it is more accurate

  #Normal Flow
  @scenarioOutline
  Scenario Outline: Update an existing category with a new description
    Given an existing category "<existing_category>" with description "<existing_desc>"
    When I update the existing description to "<new_desc>"
    Then the expected category status code received from the system is "<statusCode>"
    And the existing category's description should be "<new_desc>"
    Examples:
      | existing_category | existing_desc          | new_desc   | statusCode |
      | pottery           | clay, glazes and stuff | clay stuff | 200        |

  #Alternate flow
  @scenarioOutline
  Scenario Outline: Update an existing category with the same description
    Given an existing category "<existing_category>" with description "<existing_desc>"
    When I update the existing description to "<new_desc>"
    Then the expected category status code received from the system is "<statusCode>"
    And the existing category's description should be "<new_desc>"
    Examples:
      | existing_category | existing_desc          | new_desc               | statusCode |
      | pottery           | clay, glazes and stuff | clay, glazes and stuff | 200        |

  #Error flow
  @scenarioOutline
  Scenario Outline: Update a non existing category with a description
    Given an existing category "<existing_category>" with description "<existing_desc>"
    When I update the description to "<new_desc>" of a category that doesn't exist
    Then the expected category status code received from the system is "<statusCode>"
    Examples:
      | existing_category | existing_desc          | new_desc               | statusCode |
      | pottery           | clay, glazes and stuff | clay, glazes and stuff | 404        |