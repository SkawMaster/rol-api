Feature: Query users resource.

  As an authenticated customer of users resource
  I should be able to get the list of resources.

  The list must be paginated by default to 20 elements.

  It should let select page and size to return.
  It should response empty list if not exist elements in this page.
  It should response pagination data of current page.

  It should response an error 401 if I am not authenticate


  Background:
    Given An user ADMIN with password ADMIN


  Scenario: Request a list of users without set pagination logged as
            an user with privileges, with only one page of data.

    Given I am authenticated as ADMIN user with password ADMIN
    And Exist 10 generic users
    When I get resource /users
    Then I should get a status code 200
    And I should get a response with a list of 11 users
    And I should get pagination total count with value 11
    And I should not get pagination links


  Scenario: Request a list of users of page 0 logged as
            an user with privileges, with only one page of data.

    Given I am authenticated as ADMIN user with password ADMIN
    And Exist 10 generic users
    When I get resource /users with page 0
    Then I should get a status code 200
    And I should get a response with a list of 11 users
    And I should get pagination total count with value 11
    And I should not get pagination links


  Scenario: Request a list of users without set pagination logged as
            an user with privileges, with two pages of data.

    Given I am authenticated as ADMIN user with password ADMIN
    And Exist 20 generic users
    When I get resource /users
    Then I should get a status code 200
    And I should get a response with a list of 20 users
    And I should get pagination total count with value 21
    And I should get pagination next link
    And I should get pagination last link


  Scenario: Request a list of users of page 0 logged as
            an user with privileges, with two pages of data.

    Given I am authenticated as ADMIN user with password ADMIN
    And Exist 20 generic users
    When I get resource /users with page 0
    Then I should get a status code 200
    And I should get a response with a list of 20 users
    And I should get pagination total count with value 21
    And I should get pagination next link
    And I should get pagination last link


  Scenario: Request a list of users of page 1 logged as
            an user with privileges, with two pages of data.

    Given I am authenticated as ADMIN user with password ADMIN
    And Exist 20 generic users
    When I get resource /users with page 1
    Then I should get a status code 200
    And I should get a response with a list of 1 users
    And I should get pagination total count with value 21
    And I should get pagination prev link
    And I should get pagination first link


  Scenario: Query users resource as unauthenticated user

    When I get resource /users
    Then I should get a status code 401