@smoke @regression
Feature: User Login Functionality
  As a user
  I want to be able to login to the application
  So that I can access the application features

  Background:
    Given user is on the login screen

  @smoke @positive
  Scenario: Successful login with valid credentials
    When user logs in with username "testuser" and password "testpass"
    Then login should be successful
    And user should see welcome message

  @smoke @negative
  Scenario: Failed login with invalid username
    When user logs in with username "invaliduser" and password "testpass"
    Then login should fail
    And user should see error message "Invalid username or password"

  @smoke @negative
  Scenario: Failed login with invalid password
    When user logs in with username "testuser" and password "invalidpass"
    Then login should fail
    And user should see error message "Invalid username or password"

  @regression @negative
  Scenario: Failed login with empty credentials
    When user logs in with username "" and password ""
    Then login should fail
    And user should see error message "Username and password are required"

  @regression
  Scenario Outline: Login with multiple credentials
    When user enters username "<username>"
    And user enters password "<password>"
    And user clicks on login button
    Then login should be <result>

    Examples:
      | username  | password  | result     |
      | testuser  | testpass  | successful |
      | admin     | admin123  | successful |
      | user1     | wrongpass | fail       |
      | wronguser | testpass  | fail       |
