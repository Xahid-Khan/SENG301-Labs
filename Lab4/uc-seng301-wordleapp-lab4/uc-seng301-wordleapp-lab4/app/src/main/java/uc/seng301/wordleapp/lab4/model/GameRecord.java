package uc.seng301.wordleapp.lab4.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "gamerecord")
public class GameRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_gamerecord")
    private Long gameRecordId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user")
    private User user;

    // added in lab 2
    private Date timestamp;
    private String word;
    private int numGuesses;

    public GameRecord() {
        // JPA requirement to have a (package-private) empty constructor
        timestamp = new Date();
    }

    /* Getters and setters omitted */

    public Long getGameRecordId() {
        return gameRecordId;
    }

    public void setGameRecordId(Long gameRecordId) {
        this.gameRecordId = gameRecordId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getNumGuesses() {
        return numGuesses;
    }

    public void setNumGuesses(int numGuesses) {
        this.numGuesses = numGuesses;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date date) {
        this.timestamp = date;
    }

    @Override
    public String toString() {
        return "GameRecord{" +
                "gameRecordId=" + gameRecordId +
                ", user=" + user +
                ", word='" + word + '\'' +
                ", numGuesses=" + numGuesses +
                ", timestamp=" + timestamp +
                '}';
    }


}
