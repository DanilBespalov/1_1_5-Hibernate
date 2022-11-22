package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import org.hibernate.*;

import java.util.ArrayList;
import java.util.List;


public class UserDaoHibernateImpl implements UserDao {
    private static final SessionFactory factory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {

    }

    @Override
    //@Transactional
    public void createUsersTable() {
        Transaction transaction = null;

        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS user" +
                    "(id INTEGER not NULL AUTO_INCREMENT, " +
                    " name VARCHAR(45) not NULL, " +
                    " lastname VARCHAR(45) not NULL, " +
                    " age INTEGER(3) not NULL, " +
                    " PRIMARY KEY (id))").executeUpdate();

            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    //@Transactional
    public void dropUsersTable() {
        Transaction transaction = null;

        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("DROP table IF EXISTS users").executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    //@Transactional
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;

        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            transaction.commit();
            System.out.println("User c именем " + name + " добавлен в базу данных");
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    //@Transactional
    public void removeUserById(long id) {
        Transaction transaction = null;

        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
//            session.createQuery("delete User where id = :id").executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    //@Transactional
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        Transaction transaction = null;

        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            userList = session.createQuery("FROM User").getResultList();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return userList;
    }

    @Override
    //@Transactional
    public void cleanUsersTable() {
        Transaction transaction = null;

        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("TRUNCATE TABLE users").executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

}
