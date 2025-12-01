package com.pos.Utils;

import com.pos.Data.Entities.Check;
import com.pos.Data.Entities.Goods;
import com.pos.Data.Entities.Pos;
import com.pos.Data.Entities.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactoryUtil {
    private static SessionFactory sessionFactory;

    private HibernateSessionFactoryUtil() {}

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().configure(
                        "com/pos/Data/Dao/impl/hibernate.cfg.xml");
                configuration.addAnnotatedClass(Check.class);//FIXME временно нужно закоментить, чтобы отсечь ошибки
                configuration.addAnnotatedClass(Goods.class);//FIXME временно нужно закоментить, чтобы отсечь ошибки
                configuration.addAnnotatedClass(Pos.class);//FIXME временно нужно закоментить, чтобы отсечь ошибки
                configuration.addAnnotatedClass(User.class);
                StandardServiceRegistryBuilder builder =
                        new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());

            } catch (Exception e) {
                System.out.println("Исключение!" + e);
            }
        }
        return sessionFactory;
    }
}