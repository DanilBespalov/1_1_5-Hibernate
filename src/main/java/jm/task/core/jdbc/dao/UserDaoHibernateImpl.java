package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.TransactionException;
import org.hibernate.query.Query;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;


public class UserDaoHibernateImpl implements UserDao {
    private static final SessionFactory factory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {

    }


    @Override
    //@Transactional
    public void createUsersTable() {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("CREATE table users (id long NOT NULL AUTO_INCREMENT PRIMARY KEY, name varchar(45) not null , lastName varchar(45) not null , age int not null)");
            session.getTransaction().commit();
        } catch (TransactionException e) {
            factory.openSession().getTransaction().rollback();
        }
    }

    @Override
    //@Transactional
    public void dropUsersTable() {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("DROP table users");
            session.getTransaction().commit();
        } catch (TransactionException e) {
            factory.openSession().getTransaction().rollback();
        }
    }

    @Override
    //@Transactional
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = factory.openSession()) {
            User user = new User(name, lastName, age);
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            System.out.println("User c именем " + name + " добавлен в базу данных");

        } catch (TransactionException e) {
            factory.openSession().getTransaction().rollback();
        }
    }

    @Override
    //@Transactional
    public void removeUserById(long id) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
//            session.createQuery("delete User where id = :id").executeUpdate();
            session.getTransaction().commit();
        } catch (TransactionException e) {
            factory.openSession().getTransaction().rollback();
        }
    }

    @Override
    //@Transactional
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();

        try (Session session = factory.openSession()) {
            session.beginTransaction();
            userList = session.createQuery("from User", User.class).getResultList();

            for (User u : userList) {
                System.out.println(u);
            }

            session.getTransaction().commit();
        } catch (TransactionException e) {
            factory.openSession().getTransaction().rollback();
        }
        return userList;
    }

    @Override
    //@Transactional
    public void cleanUsersTable() {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE TABLE users");
            session.getTransaction().commit();
        } catch (TransactionException e) {
            factory.openSession().getTransaction().rollback();
        }
    }
}
