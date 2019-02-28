Feature: Testing the greeting service for demonstrating Karate

  Background:
    * url baseAPIUrl


  Scenario Outline: When a POST is called for <greetingName> and greeting "<greeting>"

    Given path 'greet'
    And request { name: <greetingName>, greeting: <greeting> }
    When method POST
    Then status 201

    * def location = responseHeaders['Location'][0]

    And match location contains 'karate/hw/greet/<greetingName>'


    Examples:
      | greetingName      | greeting        |
      #201 = Created (Location should be valid)
      | personOne         | Hello personOne |
      | personTwo         | Hello personTwo |


  Scenario Outline: When a <method> is called for <greetingName> and greeting "<greeting>"

    Given path 'greet'
    And request { name: <greetingName>, greeting: <greeting> }
    When method <method>
    Then status <status>

    Examples:
      | greetingName      | method | status | greeting    |
      #422 = Unprocessable Entity (WebDAV) (RFC 4918) - The pwesonName already exists
      | personOne         | POST   | 422    | Hello WORLD |
      # 405 not allowed for GET
      | personOne         | GET    | 405    | Hello WORLD |


  Scenario Outline: When a GET is called for <greetingName>

    Given path 'greet/<greetingName>'
    When method GET
    Then status <status>

    Examples:
      | greetingName       | status |
      | personNonExisting  | 404    |

  Scenario Outline: When a GET is called for <greetingName>

    * def waitSeconds =
    """
    function(x) {
      java.lang.Thread.sleep(x * 1000);
    }
    """

    * def convertDate =
    """
    function(x) {
      var dateTimeFormat = new java.text.SimpleDateFormat('dd/MM/yyyy HH:mm:ss');
      dateTimeFormat.setTimeZone(java.util.TimeZone.getTimeZone('UTC'));
      var dateTime = dateTimeFormat.parse(x);

      var localDateTimeFormat = new java.text.SimpleDateFormat('dd/MM/yyyy HH:mm:ss');
      localDateTimeFormat.setTimeZone(java.util.TimeZone.getTimeZone('America/Sao_Paulo'));
      return localDateTimeFormat.format(dateTime);
    }
    """

    Given path 'greet/<greetingName>'
    When call waitSeconds 5
    And method GET
    Then status <status>
    Then call waitSeconds 5

    * def firstResponse = response


    Given path 'greet/<greetingName>'
    When call waitSeconds 5
    And method GET
    Then status <status>
    And assert response['name'] == firstResponse['name']
    And assert response['lastHandshake'] != firstResponse['lastHandshake']
    And print response['greeting']

    * def lastHandshakeTime = firstResponse['lastHandshake']

    And print (firstResponse['name'] + ' says FROM PROCEDURE [Hello <greetingName>] at ' + lastHandshakeTime)
    And assert response['greeting'] == (firstResponse['name'] + ' says FROM PROCEDURE [Hello <greetingName>] at ' + lastHandshakeTime)


    Examples:
      | greetingName       | status |
      | personOne          | 200    |
      | personTwo          | 200    |

  Scenario Outline: DELETE Scenario for <greetingName>

    Given path 'greet/<greetingName>'
    When method DELETE
    Then status <status>

    Examples:
      | greetingName      | status |
      #204 = No content (It has been removed)
      | personOne         | 204    |
      | personTwo         | 204    |
      #404 = Not found (nothing to remove)
      | personOne         | 404    |
      | personTwo         | 404    |