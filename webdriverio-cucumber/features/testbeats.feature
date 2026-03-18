Feature: TestBeats Website

  Scenario: Should have the correct title on Home page - PASS
    Given I am on the home page
    Then the title should be "TestBeats"

  Scenario: Should have the correct title on Home page - FAIL
    Given I am on the home page
    Then the title should be "Wrong Title That Does Not Exist"

  Scenario: Should have the correct title on Pricing page - PASS
    Given I am on the pricing page
    Then the title should be "TestBeats Pricing"

  Scenario: Should have the correct title on Pricing page - FAIL
    Given I am on the pricing page
    Then the title should be "Wrong Title That Does Not Exist"
