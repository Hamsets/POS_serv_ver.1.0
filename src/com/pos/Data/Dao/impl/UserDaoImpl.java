package com.pos.Data.Dao.impl;

import com.pos.Data.Connection.DataBaseManager;
import com.pos.Data.Dao.UserDao;
import com.pos.Data.Entities.Pos;
import com.pos.Data.Entities.Role;
import com.pos.Data.Entities.User;
import com.pos.Service.Dto.UserDto;
import com.pos.Utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

    @Override
    public boolean createUser(User user) {
        Session session = null;
        boolean created = false;
        try {
            session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            created = true;
        } catch (Exception e){
            System.out.println("Ошибка создания пользователя в БД.");
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return created;
    }

    @Override
    public User findUserById(int id) {
        Session session = null;
        User user = new User();
        try {
            session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            user = session.load(User.class, id);
        }catch (Exception e){
            System.out.println("Ошибка поиска пользователя в БД по id.");
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return user;
    }

    @Override
    public ArrayList<User> findUserByRole(String role) {
        ArrayList<User> userArrayList =  new ArrayList<>();
        Session session = null;
        try {
        session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("From User Where role = " + role);
        userArrayList = (ArrayList<User>) query.list();
        session.getTransaction().commit();
        }catch (Exception e){
            System.out.println("Ошибка поиска пользователей по роли в БД.");
            e.printStackTrace();
        }finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return userArrayList;
    }

    @Override
    public User compareUser(UserDto userDto) {
        User user = userDto.getEntity();
        Session session = null;
        try {

            session = HibernateSessionFactoryUtil.getSessionFactory().openSession();

            Query query = session.createQuery("From User where email = '" + userDto.getEmail()
                    + "' and password = '" + userDto.getPassword() + "' and deleted = false");
            List<User> arr;
            arr = (List<User>) query.list();
            user = arr.get(0);
            return user;
        } catch (Exception e){
            System.out.println("Ошибка авторизации!");
            e.printStackTrace();
            return null;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public ArrayList<User> findUserByRating(BigDecimal low, BigDecimal high) {
        ArrayList<User> userArrayList =  new ArrayList<>();
        Session session = null;
        try {
            session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createQuery("From User Where rating > "
                    + low.toString() + " and rating < " + high.toString());
            userArrayList = (ArrayList<User>) query.list();
            session.getTransaction().commit();
        }catch (Exception e){
            System.out.println("Ошибка поиска пользователей по диапазону рейтинга в БД.");
            e.printStackTrace();
        }finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return userArrayList;
    }

    @Override
    public ArrayList<User> findAllUsers() {
        ArrayList<User> userArrayList =  new ArrayList<>();
        Session session = null;
        try {
            session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            userArrayList = (ArrayList<User>) session.createQuery("From User").list();
        }catch (Exception e){
            System.out.println("Ошибка получения всех пользователей из БД.");
            e.printStackTrace();
        }finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return userArrayList;
    }

    @Override
    public boolean updateUser(User user) {
        Session session = null;
        boolean updated = false;
        try {
            session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
            updated = true;
        }catch (Exception e){
            System.out.println("Ошибка обновления пользователя в БД.");
            e.printStackTrace();
        }finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return updated;
    }

    @Override
    public boolean deleteById(int id) {
        Session session = null;
        boolean deleted = false;
        try {
            User user = HibernateSessionFactoryUtil.getSessionFactory().openSession().get(User.class, id);
            user.setDeleted(true);
            session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
            deleted = true;
        }catch (Exception e){
            System.out.println("Ошибка удаления пользователя в БД по id.");
            e.printStackTrace();
        }finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return deleted;
    }

//    private User mapRow(ResultSet resultSet) throws SQLException {
//        User user = new User(
//                resultSet.getInt("id"),
//                resultSet.getString("first_name"),
//                resultSet.getString("last_name"),
//                resultSet.getString("sur_name"),
//                resultSet.getString("email"),
//                resultSet.getString("password"),
//                resultSet.getString("role"),
//                resultSet.getBigDecimal("rating"),
//                resultSet.getBoolean("deleted"));
//        return user;
//    }
}
