Feature: U1 - Create a new game record

  Scenario: AC1 - Create a game record with player, and number of guesses
    Given I have a player "John"
    When I create a game record with the player, word "crane", and 4 guesses
    Then The game record is created with the correct user, word, number of guesses, and the current timestamp