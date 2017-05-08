Feature: Send authentication error for unauthenticated users.
  In order to avoid show the list of users to anybody.
  As anonymous user.
  Want obtain a authentication error response.
  
  Scenario: Query user list without authentication.
    When I get resource "/users"
    Then I should get a status code 401
