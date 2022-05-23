Feature: U2 - As a player, I want to retrieve possible guesses so that I can see possible options
  Scenario: AC1 - I can get a list of possible words given the letters I know
    Given I know the letters "th..." of the word
    When I get a list of possible words
    Then The available options are returned with only those that match

  Scenario: AC2 - I can get a list of all possible words
    Given I know the letters "" of the word
    When I get a list of possible words
    Then All available words are returned

  Scenario: AC3 - If there are no words that match none are returned
    Given I know the letters "aaaaa" of the word
    When I get a list of possible words
    Then No words are returned