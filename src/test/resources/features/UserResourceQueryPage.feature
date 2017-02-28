Feature: Query users resource page

  As an authenticated customer of users resource
  I should be able to get a page of users

  It should response an error 401 if I am not authenticate

  Scenario: Query users resource as authenticated user
    Given I am authenticated as ADMIN
    When I request users resource
    Then I should get a response with HTTP status code 200

  Scenario: Query users resource as unauthenticated user
    When I request users resource
    Then I should get a response with HTTP status code 401