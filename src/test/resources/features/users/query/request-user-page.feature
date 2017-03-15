Feature: Send page data when request an user page
  In order to know pagination data about an user page.
  As logged user.
  Want obtain data about the total users and another users pages.

  Scenario Outline: Query an users page
    Given An user UserTest with password 1234
    And Exist 50 generic users
    And I am authenticated as UserTest user with password 1234
    When I get resource /users with page <page>
    Then I should get a status code 200
    And I should get a response with a list of <number> users
    And I should get pagination total count with value 51
    And I should get pagination links: <links>
    And I should get all users with the following properties: username

    Examples:
      | page | links                   | number |
      | 0    | next, last              | 20     |
      | 1    | next, last, prev, first | 20     |
      | 2    | prev, first             | 11     |
