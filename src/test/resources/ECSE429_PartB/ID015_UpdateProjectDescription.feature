Feature: Update the description
  As a user,
  I want to be able to change a project’s description,
  so that I can provide up-to-date information and details.

  # Normal Flow
  @scenarioOutline
  Scenario Outline: Update a project's description
    Given a project with description "<description>" exists in the system
    When I update the description of the project to "<new_description>"
    Then the expected project status code received should be <status_code>
    And the project description should be "<new_description>"
    Examples:
      | description   | new_description | status_code |
      | OGDescription | NewDescription  | 200         |

  # Alternate Flow (Update a description to null; result should be no description)
  @scenarioOutline
  Scenario Outline: Update project description to null (value itself not string)
    Given a project with description "<description>" exists in the system
    When I update the description of the project to "null"
    Then the expected project status code received should be <status_code>
    And the project description should be ""
    Examples:
      | description     | status_code |
      | My description. | 200         |

  # Error Flow (Update a description for a project that doesn't exist)
  @scenarioOutline
  Scenario Outline: Update project description for non-existent ID
    When I update the description of a project with an invalid ID "<invalid_id>" to "<description>"
    Then the expected project status code received should be <status_code>
    And an error message "<error>" will be returned
    Examples:
      | status_code | invalid_id | description    | error                               |
      | 404         | 404        | NewDescription | Invalid GUID for 404 entity project |