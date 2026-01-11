Feature: Regres

  In order to keep Regres api stable
  As a tester
  I want to make sure that everything works as expected

  Scenario: Create a new user
    Given I create a new user
    Then I fetch user by id

  Scenario: Fetch All Users
    Given I fetch all users



