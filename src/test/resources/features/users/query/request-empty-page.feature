Feature: Send empty response when request a no users data page.
  In order to know that a page has not any data.
  As logged user.
  Want obtain an empty response.

  Scenario: Query users on a empty page.
    Given An user UserTest with password 1234
    And I am authenticated as UserTest user with password 1234
    When I get resource "/users" with page 1
    Then I should get a status code 200
    And I should get a response with a list of 0 users
