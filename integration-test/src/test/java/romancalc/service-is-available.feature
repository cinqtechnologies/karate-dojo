Feature: Roman Calculator

  Background:
    * url baseAPIUrl

  Scenario: Servidor deve estar disponível

#    Given path 'ping'
    When method GET
    Then status 200
    And match response == 'PONG'