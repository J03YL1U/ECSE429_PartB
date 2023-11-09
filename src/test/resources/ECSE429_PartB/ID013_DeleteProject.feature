Feature: Delete project
  As a user,
  I want to be able to delete a project,
  so that I can keep my list of projects up-to-date and organised.

  # Normal Flow
  @scenarioOutline
  Scenario Outline: 
    Given a project with title "<title>" exists in the system
    When I delete a project with id "<id>"
    Then the expected project status code received should be <status_code>
    And the number of projects in the system will be <number_of_projects>
    Examples:
      | title         | id | status_code | number_of_projects |
      | ToDelete      | 1  | 200         | 1                  |

  # Alternate Flow (Malformed request despite being a string)
  @scenarioOutline
  Scenario Outline:
    When I delete a project with id "<id>"
    Then the expected project status code received should be <status_code>
    And the number of projects in the system will be <number_of_projects>
    Examples:
      | id  | status_code | number_of_projects |
      | #@$ | 404         | 1                  |

  # Error Flow (Deleting a project that doesn't exist)
  @scenarioOutline
  Scenario Outline:
    When I delete a project with id "<id>"
    Then the expected project status code received should be <status_code>
    And the number of projects in the system will be <number_of_projects>
    And an error message "<error>" will be returned
    Examples:
      | id  | status_code | number_of_projects | error                                          |
      | 456 | 404         | 1                  | Could not find any instances with projects/456 |
