Feature: Registering Car Type

  This feature supports the way the Administration can register and modify a
  CarType.

  Background:
    Given an emergency car type named FIREFIGHTERS which priority is 100
    And a privileged car type named GREEN_CAR which priority is 50

  Scenario: Fetch before changing anything
    When the car type FIREFIGHTERS is fetched
    Then the car type priority should be 100
    And the cars of this type should be emergency ones

  Scenario: Update the priority of a car type
    When the priority of the type FIREFIGHTERS is changed to 160
    And the car type FIREFIGHTERS is fetched
    Then the car type priority should be 160
    And the cars of this type should be emergency ones
