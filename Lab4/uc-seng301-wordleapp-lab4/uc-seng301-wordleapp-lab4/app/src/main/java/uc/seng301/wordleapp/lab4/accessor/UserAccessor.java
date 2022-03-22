package uc.seng301.wordleapp.lab4.accessor;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import uc.seng301.wordleapp.lab4.model.User;

public class UserAccessor {
    private final SessionFactory sessionFactory;

    /**
     * default constructor
     * @param sessionFactory the JPA session factory to talk to the persistence implementation.
     */
    public UserAccessor(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Gets user from persistence by name
     * @param name name of user to fetch
     * @return User with given name
     */
    public User getUserByName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name '" + name + "' cannot be null or blank");
        }
        User user = null;
        try (Session session = sessionFactory.openSession()) {
            user = session.createQuery("FROM User WHERE userName='"+name+"'", User.class).uniqueResult();
        } catch (HibernateException e){
            System.out.println("unable to get user by name '"+name+"'");
            e.printStackTrace();
        }
        return user;
    }

    /**
     * Gets user from persistence by id
     * @param userId id of user to fetch
     * @return User with given id
     */
    public User getUserById(Long userId) {
        if (null == userId) {
            throw new IllegalArgumentException("cannot retrieve user with null id");
        }
        User user = null;
        try (Session session = sessionFactory.openSession()) {
            user = session.createQuery("FROM User WHERE userId =" + userId, User.class)
                    .uniqueResult();
        } catch (HibernateException e) {
            System.out.println("unable to get user by id '"+userId+"'");
            e.printStackTrace();
        }
        return user;
    }

    /**
     * Saves user to persistence
     * @param user user to save
     * @return id of saved user
     * @throws IllegalArgumentException if user object is invalid (e.g. missing properties)
     */
    public Long persistUser(User user) throws IllegalArgumentException {
        if (null == user || null == user.getUserName() || user.getUserName().isBlank()) {
            throw new IllegalArgumentException("cannot save null or blank user");
        }

        User existingUser = getUserByName(user.getUserName());
        if (null != existingUser) {
            user.setUserId(existingUser.getUserId());
            return existingUser.getUserId();
        }

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (HibernateException e) {
            System.out.println("unable to persist user with name '"+user.getUserName()+"'");
            e.printStackTrace();
        }
        return user.getUserId();
    }

    /**
     * remove given user from persistence by id
     * @param userId id of user to be deleted
     * @return true if the record is deleted
     */
    public boolean removeUserById(Long userId) {
        User user = getUserById(userId);
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(user);
            transaction.commit();
            return true;
        } catch (HibernateException e) {
            System.out.println("unable to persist user with id '"+userId+"'");
            e.printStackTrace();
        }
        return false;
    }

}
