package uc.seng301.wordleapp.lab5.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "game_user")
public class User {
    @Id
    @Column(name = "id_user")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    private String userName;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user")
    private List<GameRecord> gameRecords;

    public User() {
        // JPA requirement to have a (package-private) empty constructor
    }

    /* Getters and setters omitted */

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String name) {
        this.userName = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                (gameRecords != null?", gameRecordIds=" + gameRecords.stream().map(GameRecord::getGameRecordId).toList()
                : "") +
                '}';
    }
}
