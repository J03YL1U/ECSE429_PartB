Feature: Create project
  As a user,
  I want to be able to create new projects,
  so that I can manage my todos.

  # Normal Flow
  @scenarioOutline
  Scenario Outline: Creating a new project
    When I create a project with title "<title>", description "<description>", completed status "<completed>", active status "<active>"
    Then the expected project status code received should be <status_code>
    And the number of projects in the system will be <number_of_projects>
    And a project with title "<title>", description "<description>", completed status "<completed>", active status "<active>" will be in the list of projects
    Examples:
      | title             | description                | completed | active | status_code | number_of_projects |
      | My Second Project | This is my second project. | true      | false  | 201         | 2                  |
      | This Project      | This is just this project. | false     | false  | 201         | 2                  |

  # Alternate Flow (Creating a project with a provided id)
  @scenarioOutline
  Scenario Outline: Creating a new project with a provided id
    When I create a project with id <id>
    Then the expected project status code received should be <status_code>
    And the number of projects in the system will be <number_of_projects>
    And an error message "<error>" will be returned
    Examples:
      | id | status_code | number_of_projects | error                                                              |
      | 47 | 400         | 1                  | Invalid Creation: Failed Validation: Not allowed to create with id |

  # Error Flow (Create a project with invalid type for input fields)
  @scenarioOutline
  Scenario Outline: Creating a new project with the invalid types for statuses
    When I create a project with title "<title>", description "<description>", completed status "<completed>", active status "<active>"
    Then the expected project status code received should be <status_code>
    And the number of projects in the system will be <number_of_projects>
    And an error message "<error>" will be returned
    Examples:
      | title | description | completed | active | status_code | number_of_projects | error                                          |
      | Title | Desc        | yes       | true   | 400         | 1                  | Failed Validation: completed should be BOOLEAN |
      | Title | Desc        | false     | 12     | 400         | 1                  | Failed Validation: active should be BOOLEAN    |

