Feature: Roman Calculator

  Background:
    * url baseAPIUrl
    * def operation = read('math-operator.js')
    * def isOperator = read('is-operator.js')


  Scenario Outline: Operação de Soma [<first> <operation> <second> = <resultFileName>]

    * call read('service-is-available.feature')

    Given path 'calc', '<first>', operation('<operation>'), '<second>'
    When method GET
    Then status <status>
    And match response == read('samples/<resultFileName>')


  Examples:
    | first | second    | operation | status | resultFileName          |
    | MMD   | MCDXCIX   | +         | 200    | soma-MMD-MCDXCIX.json   |
    | MMD   | MMCDXCIX  | sum       | 400    | soma-MMD-MMCDXCIX.json  |
    | MMD   | MMCDXCIXK | +         | 422    | soma-MMD-MMCDXCIXK.json |
    | MMD   | MCDXC     | plus      | 422    | soma-MMD-MCDXC.json     |


  Scenario Outline: Operação de Subtração [<first> <operation> <second> = <resultFileName>]

    * call read('service-is-available.feature')
    * def op = operation('<operation>')

    Given path 'calc', '<first>', op, '<second>'
    When method GET
    Then status <status>
    And match response == read('samples/<resultFileName>')


    Examples:
      | first  | second   | operation | status | resultFileName              |
      | X      | IX       | -         | 200    | subtracao-X-IX.json         |
      | X      | X        | subtract  | 200    | subtracao-X-X.json          |
      | SIX    | X        | -         | 422    | subtracao-SIX-X.json        |


  Scenario Outline: Operação de Multiplicação [<first> <operation> <second> = <resultFileName>]

    * call read('service-is-available.feature')
    * def second = Java.type("romancalc.RomanCalcUtil").removeNonRomans('<second>')

    Given path 'calc', '<first>', operation('<operation>'), second
    When method GET
    Then status <status>
    And match response == read('samples/<resultFileName>')


    Examples:
      | first      | second     | operation | status | resultFileName                  |
      | MCCCXXXIII | III        | *         | 200    | multiplicar-MCCCXXXIII-III.json |
      | III        | MCCCXXXIII | multiply  | 200    | multiplicar-III-MCCCXXXIII.json |
      | IV         | MCCCXXXIII | *         | 400    | multiplicar-IV-MCCCXXXIII.json  |
      | I          | MMCXCINQX  | *         | 200    | multiplicar-I-MMCXCINQX.json    |


  Scenario Outline: Operação de Divisão [<first> <operation> <second> = <resultFileName>]

    * call read('service-is-available.feature')

    Given path 'calc', '<first>', operation('<operation>'), '<second>'
    When method GET
    Then status <status>
    And match response == read('samples/<resultFileName>')


    Examples:
      | first | second   | operation | status | resultFileName    |
      | XX    | X        | /         | 200    | dividir-XX-X.json |
      | X     | XX       | /         | 200    | dividir-X-XX.json |