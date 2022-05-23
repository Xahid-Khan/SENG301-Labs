package uc.seng301.wordleapp.lab5.dictionary;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class DictionaryQueryImpl extends DictionaryQuery {
    private static final Logger LOGGER = LogManager.getLogger(DictionaryQueryImpl.class);
    private static final String SOLVER_URL = "https://seng301.csse.canterbury.ac.nz/solver/";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public DictionaryResponse guessWord(String query) {
        if(query.equals(""))
            query = ".....";
        DictionaryResponse res = null;
        try {
            URL url = new URL(SOLVER_URL + query);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int responseCode = connection.getResponseCode();

            if (responseCode != 200) {
                LOGGER.error("unable to process request to solver, response code is '{}'", responseCode);
                return null;
            }
            Scanner scanner = new Scanner(url.openStream());
            StringBuilder stringResult = new StringBuilder();
            while (scanner.hasNext()) {
                stringResult.append(scanner.nextLine());
            }
            scanner.close();
            res = objectMapper.readValue(stringResult.toString(), DictionaryResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;

    }
}
