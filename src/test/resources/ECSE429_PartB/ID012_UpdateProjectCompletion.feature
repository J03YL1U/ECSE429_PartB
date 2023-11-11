Feature: Update project completion status
  As a user,
  I want to be able to update a project’s completion status,
  so that I can track and keep project progress up-to-date.

  # Normal Flow
  @scenarioOutline
  Scenario Outline: Update a project's completion status
    Given a project with completed status "<completed>" exists in the system
    When I update the completed status of the project to "<new_completed>"
    Then the expected project status code received should be <status_code>
    And the completed status of the project will be "<new_completed>"
    Examples:
      | completed | new_completed | status_code |
      | false     | true          | 200         |
      | true      | false         | 200         |

  # Alternate Flow (project ID doesn't exist)
  @scenarioOutline
  Scenario Outline: Update a project's completion that doesn't exist
    When I update the completed status of a project with id <invalid_id> to "<new_completed>"
    Then the expected project status code received should be <status_code>
    And an error message "<error>" will be returned
    Examples:
      | invalid_id | new_completed | status_code | error                                        |
      | 777        | true          | 404         | Invalid GUID for 777 entity project          |

  # Error Flow (invalid type for completed)
  @scenarioOutline
  Scenario Outline: Update a project's completion with invalid type
    Given a project with completed status "<completed>" exists in the system
    When I update the completed status of the project to "<new_completed>"
    Then the expected project status code received should be <status_code>
    And the completed status of the project will be "<completed>"
    And an error message "<error>" will be returned
    Examples:
      | completed | new_completed | status_code | error                                          |
      | false     | @             | 400         | Failed Validation: completed should be BOOLEAN |
      | true      | 234           | 400         | Failed Validation: completed should be BOOLEAN |