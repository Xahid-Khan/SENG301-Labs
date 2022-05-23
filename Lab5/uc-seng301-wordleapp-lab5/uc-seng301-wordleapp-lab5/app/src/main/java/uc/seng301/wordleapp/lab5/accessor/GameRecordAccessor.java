package uc.seng301.wordleapp.lab5.accessor;

import org.hibernate.*;
import uc.seng301.wordleapp.lab5.model.GameRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * This class offers helper methods for saving, removing, and fetching game records from persistence
 * {@link GameRecord} entities
 */
public class GameRecordAccessor {
    private final SessionFactory sessionFactory;

    /**
     * default constructor
     * @param sessionFactory the JPA session factory to talk to the persistence implementation.
     */
    public GameRecordAccessor(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Get game object from persistence layer
     * @param gameRecordId id of game record to fetch
     * @return Gamerecord with id given if it exists in database, no user object
     */
    public GameRecord getGameRecordById(Long gameRecordId) {
        if (null == gameRecordId) {
            throw new IllegalArgumentException("cannot retrieve game record with null id");
        }
        GameRecord gameRecord = null;
        try (Session session = sessionFactory.openSession()) {
            gameRecord = session.createQuery("FROM GameRecord WHERE gameRecordId =" + gameRecordId, GameRecord.class).uniqueResult();
        } catch (HibernateException e) {
            System.err.println("Unable to open session or transaction");
            e.printStackTrace();
        }
        return gameRecord;
    }

    /**
     * Gets gamerecord and associated user from persistence layer
     * @param gameRecordId id of game record to fetch
     * @return Gamerecord with id given if it exists in database with user object
     */
    public GameRecord getGameRecordAndUserById(Long gameRecordId) {
        if (null == gameRecordId) {
            throw new IllegalArgumentException("cannot retrieve gameRecord with null id");
        }
        GameRecord gameRecord = null;
        try (Session session = sessionFactory.openSession()) {
            gameRecord = session.createQuery("FROM GameRecord WHERE gameRecordId =" + gameRecordId, GameRecord.class).uniqueResult();
            if (null != gameRecord) {
                Hibernate.initialize(gameRecord.getUser());
            }
        } catch (HibernateException e) {
            System.err.println("Unable to open session or transaction");
            e.printStackTrace();
        }
        return gameRecord;
    }

    /**
     * Gets top 10 gamerecords from database (in ascending number of guesses)
     * @return top 10 gamerecords
     */
    public List<GameRecord> getHighscores() {
        List<GameRecord> highscores = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            highscores = session.createQuery("FROM GameRecord ORDER BY numGuesses ASC", GameRecord.class).setMaxResults(10).list();
        } catch (HibernateException e) {
            System.err.println("Unable to open session or transaction");
            e.printStackTrace();
        }
        return highscores;
    }

    /**
     * Save given gamerecord to persistence
     * @param gameRecord gamerecord to be saved
     * @return The id of the persisted gamerecord
     */
    public Long persistGameRecord(GameRecord gameRecord) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(gameRecord);
            transaction.commit();
        } catch (HibernateException e) {
            System.err.println("Unable to open session or transaction");
            e.printStackTrace();
        }
        return gameRecord.getGameRecordId();
    }

    /**
     * remove given gamerecord from persistence by id
     * @param gameRecordId id of gamerecord to be deleted
     * @return true if the record is deleted
     */
    public boolean deleteGameRecordById(Long gameRecordId) throws IllegalArgumentException {
        GameRecord gameRecord = getGameRecordById(gameRecordId);
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(gameRecord);
            transaction.commit();
            return true;
        } catch (HibernateException e) {
            System.err.println("Unable to open session or transaction");
            e.printStackTrace();
        }
        return false;
    }

}

