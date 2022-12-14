/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package uc.seng301.wordleapp.lab5;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import uc.seng301.wordleapp.lab5.accessor.GameRecordAccessor;
import uc.seng301.wordleapp.lab5.accessor.UserAccessor;
import uc.seng301.wordleapp.lab5.dictionary.DictionaryQueryImpl;
import uc.seng301.wordleapp.lab5.dictionary.DictionaryResponse;
import uc.seng301.wordleapp.lab5.model.GameRecord;
import uc.seng301.wordleapp.lab5.model.User;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class App {

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GRAY = "\u001B[90m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final Logger LOGGER = LogManager.getLogger(App.class);

    private static SessionFactory sessionFactory;
    private final Scanner cli;
    private final Random random;
    private final List<String> words;
    private final DictionaryQueryImpl dictionaryQuery = new DictionaryQueryImpl();

    public App() {
        // this will load the config file (xml file in resources folder)
        Configuration configuration = new Configuration();
        configuration.configure();
        sessionFactory = configuration.buildSessionFactory();
        cli = new Scanner(System.in);
        random = new Random(301L);

        DictionaryResponse response = dictionaryQuery.guessWord("");
        words = response.getWords();

    }


    public static void  main(String [] args) {
        App app = new App();

        app.basicCli();

    }


    public void basicCli() {
        GameRecordAccessor gameRecordAccessor = new GameRecordAccessor(sessionFactory);
        UserAccessor userAccessor = new UserAccessor(sessionFactory);

        welcome();
        while (true) {
            System.out.println("Please enter your name, or type !q to exit:");
            String input = cli.nextLine();
            if (input.equals("!q"))
                return;
            User user = userAccessor.getUserByName(input);
            if (user == null) {
                user = new User();
                user.setUserName(input);
                userAccessor.persistUser(user);
            }

            String randomWord = words.get(random.nextInt(words.size()));
            LOGGER.info(String.format("Random word = %s", randomWord));

            GameRecord gameRecord = new GameRecord();
            gameRecord.setUser(user);
            gameRecord.setWord(randomWord);

            int numGuesses = 0;
            while (true) {
                System.out.println("Please enter your guess (must be a 5 letter word), or use \"help searchedpattern\" where \"searchedpattern\" like \"cran.\" or \".a...\" to view the top 5 possible guesses:");
                input = cli.nextLine();
                if (input.equals("!q"))
                    return;
                if (input.length() > 4 && input.substring(0, 5).equalsIgnoreCase("help ")) {
                    String helpString = input.split(" ")[1];
                    List<String> helpResponse = dictionaryQuery.guessWord(helpString).getWords();
                    for (int i = 0; i < Math.min(5, helpResponse.size()); i++)
                        System.out.println(helpResponse.get(i));
                    continue;
                }
                if (input.equals(gameRecord.getWord())) {
                    numGuesses++;
                    System.out.println(responseString(input, gameRecord.getWord()));
                    System.out.println(String.format("Solved in %d guesses", numGuesses));
                    break;
                } else {
                    numGuesses++;
                    System.out.println(responseString(input, gameRecord.getWord()));
                }
            }
            gameRecord.setNumGuesses(numGuesses);
            gameRecordAccessor.persistGameRecord(gameRecord);
            List<GameRecord> highScores = gameRecordAccessor.getHighscores();
            System.out.println("High Scores:");
            for (int i = 0; i < highScores.size(); i++ ) {
                System.out.println(String.format("%d: %s", i+1, highScores.get(i)));
            }
        }
    }

    /**
     * Simple welcome message for command line operation
     */
    private void welcome() {
        System.out.println("""
                ######################################################
                             Welcome to Wordle Clone App           
                ######################################################""");
    }

    /**
     * Simple colour coding wordle response for terminal
     * @param guess guessed word
     * @param word actual word
     * @return wordle response colour coded
     */
    private String responseString(String guess, String word) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i =0; i<5; i++) {
            if(word.charAt(i) == guess.charAt(i))
                stringBuilder.append(ANSI_GREEN).append(guess.charAt(i)).append(ANSI_RESET);
            else {
                boolean found = false;
                for (char c : word.toCharArray()) {
                    if (c == guess.charAt(i)) {
                        stringBuilder.append(ANSI_YELLOW).append(guess.charAt(i)).append(ANSI_RESET);
                        found = true;
                        break;
                    }
                }
                if(!found)
                    stringBuilder.append(ANSI_GRAY).append(guess.charAt(i)).append(ANSI_RESET);
            }
        }
        return stringBuilder.toString();
    }

}
