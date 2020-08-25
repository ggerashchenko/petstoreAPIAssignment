Feature: Pet management tests

  Scenario: Create new pet
    Given I add a pet to store
    Then Verify pet successfully added

  Scenario: Update existing pet
    Given I add a pet to store
    When I change availability status
    Then Verify pet successfully updated

  Scenario: Delete pet
    Given I add a pet to store
    When I delete pet
    Then Verify pet successfully deleted