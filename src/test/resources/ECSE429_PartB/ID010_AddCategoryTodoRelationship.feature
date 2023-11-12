Feature: Add Category Todo Relationship
  As a user,
  I want to be able to create a category-todo relationship,
  so that I can better organise my todos.

  #Normal Flow
  @scenarioOutline
  Scenario Outline:  Create a relationship between an existing category and an existing todo
    Given an existing category with title "<existing_category>"
    And an existing todo with title "<existing_todo>"
    When create a relationship between "<existing_category>" and "<existing_todo>"
    Then the expected category status code received from the system is "<statusCode>"
    And "<existing_todo>" will be part of "<existing_category>"
    Examples:
      | existing_category | existing_todo | statusCode |
      | pottery           | make a cup    | 201        |

  #Alternate flow
  @scenarioOutline
  Scenario Outline: Create a relationship with a category when creating a todo
    Given an existing todo with title "<existing_todo>"
    When create a new category "<new_category>" with todo "<existing_todo>"
    Then the expected category status code received from the system is "<statusCode>"
    And "<existing_todo>" will be part of "<new_category>"
    Examples:
      | new_category | existing_todo | statusCode |
      | pottery           | A2       | 201        |

  #Error flow
  @scenarioOutline
  Scenario Outline: Create a relationship between a todo and a category that doesn't exist
    When create a new todo "<new_todo>" with category that doesn't exist
    Then the expected category status code received from the system is "<statusCode>"
    Examples:
      | invalid_category | new_todo | statusCode |
      | 2                | A2       | 400        |