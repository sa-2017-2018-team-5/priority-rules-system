Feature: Getting Traffic info

  This feature supports the way we retrieve information on the traffic from an external partner.


  Scenario: Get info on a road supposed to be jam
    When a road section with id 1
    Then the road status should be jam


  Scenario: Get info on a road supposed to be free
    When a road section with id 2
    Then the road status should be free