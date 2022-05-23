package uc.seng301.wordleapp.lab5.dictionary;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

/**
 * Simple data class for jackson json deserialization
 */
public class DictionaryResponse {

    @JsonDeserialize
    @JsonProperty("words")
    private List<String> words;

    public List<String> getWords() {
        return words;
    }

    public void setWords(List<String> words) {
        this.words = words;
    }
}
