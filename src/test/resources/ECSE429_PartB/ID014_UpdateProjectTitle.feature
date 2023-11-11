Feature: Update project title
  As a user,
  I want to be able to update a project’s title,
  so that I can maintain accuracy of the project scope.

  # Normal Flow
  @scenarioOutline
  Scenario Outline: Updating a project title
    Given a project with title "<title>" exists in the system
    When I update the title of the project to "<new_title>"
    Then the expected project status code received should be <status_code>
    And the project title should be "<new_title>"
    Examples:
      | title   | new_title | status_code |
      | OGTitle | NewTitle  | 200         |

  # Alternate Flow (Update a title to null; result should be no title)
  @scenarioOutline
  Scenario Outline: Update project title to null (value itself not string)
    Given a project with title "<title>" exists in the system
    When I update the title of the project to "null"
    Then the expected project status code received should be <status_code>
    And the project title should be ""
    Examples:
      | title     | status_code |
      | MyProject | 200         |

  # Error Flow (Update a title for a project that doesn't exist)
  @scenarioOutline
  Scenario Outline: Update project title for non-existent ID
    When I update the title of a project with an invalid ID "<invalid_id>" to "<title>"
    Then the expected project status code received should be <status_code>
    And an error message "<error>" will be returned
    Examples:
      | status_code | invalid_id | title   | error                               |
      | 404         | 777        | NewName | Invalid GUID for 777 entity project |

