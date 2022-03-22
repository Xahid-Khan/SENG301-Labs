/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package uc.seng301.wordleapp.lab4;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import uc.seng301.wordleapp.lab4.model.GameRecord;
import uc.seng301.wordleapp.lab4.model.User;

public class App {

    // a JPA session factory is needed to access the actual data repository
    private static SessionFactory sessionFactory;

    public App() {
        // this will load the config file (xml file in resources folder)
        Configuration configuration = new Configuration();
        configuration.configure();
        sessionFactory = configuration.buildSessionFactory();
    }

    public static void  main(String [] args) {
        App app = new App();

        User user = new User();
        user.setUserName("John");

        GameRecord gameRecord = new GameRecord();
        gameRecord.setUser(user);
        gameRecord.setWord("crane");
        gameRecord.setNumGuesses(3);

        Transaction transaction = null;
        Long gameRecordId = -1L;
        try (Session session = sessionFactory.openSession()) {
            System.out.println("\n\nPersisting an game record with user");
            transaction = session.beginTransaction();
            session.save(user);
            gameRecordId = (Long) session.save(gameRecord);

            // persist into the database
            transaction.commit();
            System.out.println("Created game record " + gameRecord + " with user " + user);
        } catch (HibernateException e) {
            System.err.println("Unable to open session or transaction");
            e.printStackTrace();
            if (null != transaction) {
                transaction.rollback();
            }
        }

        try (Session session = sessionFactory.openSession()) {
            System.out.println("retrieve the game record with id " + gameRecordId);
            // note that HQL queries use the Java attributes names, i.e. eventId and not
            // id_event (table column)
            GameRecord retrievedGameRecord = session
                    .createQuery("FROM GameRecord WHERE gameRecordId =" + gameRecordId, GameRecord.class)
                    .uniqueResult();
            System.out.println(retrievedGameRecord);
        } catch (HibernateException e) {
            System.err.println("unable to open session or transaction");
            e.printStackTrace();
        }
    }

}