Feature: Query users resource page

  As an authenticated customer of users resource
  I should be able to get a page of users

  It should response an error 401 if I am not authenticate

  Background:
    Given An user ADMIN with password ADMIN

  Scenario: Request a list of users without set pagination
    logged as an user with privileges.
    Given I am authenticated as ADMIN user with password ADMIN
    And Exist 20 generic users
    When I get resource /users
    Then I should get a status code 200
    And I should get pagination total count with value 21
    And I should get pagination next link
    And I should get pagination last link

  Scenario: Query users resource as unauthenticated user
    When I get resource /users
    Then I should get a status code 401