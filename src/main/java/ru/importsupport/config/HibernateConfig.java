package ru.importsupport.config;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.importsupport.dto.MyTableDto;

import java.sql.SQLException;

public class HibernateConfig {
    private static SessionFactory sessionFactory;

    private HibernateConfig() {
    }

    public static Session getSession() throws SQLException {
        return sessionFactory.openSession();
    }

    public static void init(ConfigLoader configLoader) {
        Configuration configuration = new Configuration()
                .setProperties(configLoader.buildDbProperties())
                //TODO add entity to .addAnnotatedClass
                .addAnnotatedClass(MyTableDto.class);
        sessionFactory = configuration.buildSessionFactory();
    }
}