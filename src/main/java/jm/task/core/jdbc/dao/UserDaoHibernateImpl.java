package jm.task.core.jdbc.dao;

import jakarta.transaction.Transactional;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.management.Query;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static final SessionFactory factory = new Configuration()
            .configure("hibernate.cfg.xml")
            .addAnnotatedClass(User.class)
            .buildSessionFactory();


    public UserDaoHibernateImpl() {

    }


    @Override
    @Transactional
    public void createUsersTable() {
        try (Session session = factory.getCurrentSession()) {=
           session.createQuery("CREATE User (id long, name varchar(45) not null , lastName varchar(45) not null , age int not null)", User.class);

        }
    }

    @Override
    @Transactional
    public void dropUsersTable() {
        try (Session session = factory.getCurrentSession()) {
            session.createQuery("DROP User", User.class);

        }
    }

    @Override
    @Transactional
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = factory.getCurrentSession()) {
            User user = new User(name, lastName, age);
            session.createQuery("insert into User (name, lastName, age) values (?, ?, ?)", User.class);
            System.out.println("User c именем " + name + " добавлен в базу данных");
        }
    }

    @Override
    @Transactional
    public void removeUserById(long id) {
        try (Session session = factory.getCurrentSession()) {
//            session.beginTransaction();
            User user = session.get(User.class, id);
            session.remove(user);
//            session.createQuery("delete User where id = ").executeUpdate();
        }
    }

    @Override
    @Transactional
    public List<User> getAllUsers() {
        try (Session session = factory.getCurrentSession()) {
            List<User> userList = session.createQuery("from User", User.class).getResultList();

            for (User u : userList) {
                System.out.println(u);
            }

            return userList;
        }
    }

    @Override
    @Transactional
    public void cleanUsersTable() {
        try (Session session = factory.getCurrentSession()) {
            session.createQuery("TRUNCATE User", User.class);
        }
    }
}
