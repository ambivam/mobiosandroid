@qa-app @login
Feature: QA App Login Functionality
  As a user of the QA App v1.14.10
  I want to be able to login to the application
  So that I can access the app features

  Background:
    Given user opens the QA app
    And user is on the login screen

  @smoke @positive @high-priority
  Scenario: Successful login with valid credentials
    When user logs in with username "rajanikanth.bathula@mystratis.com" and password "Notallowed@123"
    Then login should be successful
    And user should see welcome message

  # @smoke @negative @high-priority
  # Scenario: Failed login with invalid username
  #   When user logs in with username "invalid@qa.com" and password "Test123!"
  #   Then login should fail
  #   And user should see error message "Invalid credentials"

  # @smoke @negative @high-priority
  # Scenario: Failed login with invalid password
  #   When user logs in with username "testuser@qa.com" and password "wrongpassword"
  #   Then login should fail
  #   And user should see error message "Invalid credentials"

  # @regression @negative
  # Scenario: Failed login with empty username
  #   When user enters username ""
  #   And user enters password "Test123!"
  #   And user clicks login button
  #   Then login should fail
  #   And user should see error message "Username is required"

  # @regression @negative
  # Scenario: Failed login with empty password
  #   When user enters username "testuser@qa.com"
  #   And user enters password ""
  #   And user clicks login button
  #   Then login should fail
  #   And user should see error message "Password is required"

  # @regression @negative
  # Scenario: Failed login with empty credentials
  #   When user enters username ""
  #   And user enters password ""
  #   And user clicks login button
  #   Then login should fail
  #   And user should see error message "Username and password are required"

  # @regression @functional
  # Scenario: Login form field validation
  #   When user enters username "testuser@qa.com"
  #   And user enters password "Test123!"
  #   And user clears username field
  #   And user clicks login button
  #   Then login should fail
  #   And user should be on login screen

  # @regression @functional
  # Scenario: Multiple login attempts
  #   When user logs in with username "wrong@qa.com" and password "wrongpass"
  #   Then login should fail
  #   When user clears username field
  #   And user clears password field
  #   And user logs in with username "testuser@qa.com" and password "Test123!"
  #   Then login should be successful

  # @regression @ui
  # Scenario Outline: Login with different credential combinations
  #   When user enters username "<username>"
  #   And user enters password "<password>"
  #   And user clicks login button
  #   Then login should be <result>

  #   Examples:
  #     | username           | password    | result     |
  #     | testuser@qa.com    | Test123!    | successful |
  #     | admin@qa.com       | Admin123!   | successful |
  #     | user1@qa.com       | wrongpass   | fail       |
  #     | wronguser@qa.com   | Test123!    | fail       |
  #     | testuser@qa.com    |             | fail       |
  #     |                    | Test123!    | fail       |

  # @regression @functional @forgot-password
  # Scenario: Forgot password functionality
  #   When user clicks forgot password link
  #   Then user should be redirected to forgot password screen

  # @performance @load-time
  # Scenario: Login page load time
  #   Given user opens the QA app
  #   Then login screen should load within 5 seconds
  #   And all login elements should be visible

  # @accessibility @a11y
  # Scenario: Login page accessibility
  #   Given user is on the login screen
  #   Then username field should have proper accessibility labels
  #   And password field should have proper accessibility labels
  #   And login button should be accessible
