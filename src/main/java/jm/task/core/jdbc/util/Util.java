package jm.task.core.jdbc.util;


import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import java.util.Properties;

public class Util {
    private static SessionFactory sessionFactory;
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

//    public static SessionFactory factory = new Configuration()
//            .configure("hibernate.cfg.xml")
//            .addAnnotatedClass(User.class)
//            .buildSessionFactory();
//    }

    public static SessionFactory getSessionFactory(){
    if (sessionFactory == null) {
        try {
            Configuration configuration = new Configuration();
            Properties settings = new Properties();
            settings.put(DRIVER, DRIVER);
            settings.put(Environment.URL, "jdbc:mysql://localhost:3306/mydb?useSSL=false");
            settings.put(Environment.USER, "root");
            settings.put(Environment.PASS, "rootroot");
            settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
            settings.put(Environment.SHOW_SQL, "true");
            settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
            settings.put(Environment.HBM2DDL_AUTO, "create");
            configuration.setProperties(settings);
            configuration.addAnnotatedClass(User.class);
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            System.out.println("Сессия создана");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Ошибка создания сессии.");
        }
    }
    return sessionFactory;
}

}
