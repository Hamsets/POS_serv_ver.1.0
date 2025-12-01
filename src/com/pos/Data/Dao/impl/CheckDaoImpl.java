package com.pos.Data.Dao.impl;

import com.pos.Data.Dao.CheckDao;
import com.pos.Data.Entities.Check;
import com.pos.Data.Entities.Pos;
import com.pos.Service.Dto.CheckDto;
import com.pos.Utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.Timestamp;
import java.util.ArrayList;

public class CheckDaoImpl implements CheckDao {

    @Override
    public int createCheck(Check check) {
        Session session = null;
        int createdHash = 0;
        try {
            session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.save(check);
            transaction.commit();
            createdHash = new CheckDto(check).hashCode();
        } catch (Exception e){
            System.out.println("Ошибка создания пользователя в БД.");
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return createdHash;
    }

    @Override
    public Check findCheckById(int id) {
        Session session = null;
        Check check = new Check();
        try {
            session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            check = session.load(Check.class, id);
        }catch (Exception e){
            System.out.println("Ошибка поиска чека в БД по id.");
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return check;
    }

    @Override
    public ArrayList<Check> findCheckByDate(Timestamp startDate, Timestamp endDate, int posId) {
        ArrayList<Check> checkArrayList =  new ArrayList<>();
        Session session = null;
        try {
            session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createQuery("From Check Where date_stamp > '"
                    + startDate.toString() + "' and date_stamp < '" + endDate.toString()
                    + "' and pos_id = " + posId + " and deleted = false");
            checkArrayList = (ArrayList<Check>) query.list();
            session.getTransaction().commit();
        }catch (Exception e){
            System.out.println("Ошибка поиска чеков по диапазону дат в БД.");
            e.printStackTrace();
        }finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return checkArrayList;
    }

    @Override
    public ArrayList<Check> findCheckByPos(Pos pos) {
        ArrayList<Check> checkArrayList = new ArrayList<>();
        Session session = null;
        try {
            session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            Query query = session.createQuery("From Check Where pos = " + pos.getPosId());
            checkArrayList = (ArrayList<Check>) query.list();
        }catch (Exception e){
            System.out.println("Ошибка поиска чеков по POS в БД.");
            e.printStackTrace();
        }finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return checkArrayList;

    }

    //TODO вообще убрать подтверждение чека для записи
//    @Override
//    public void updateCheckAccepted(String inputStr) {
//        String[] arrayInputStr = inputStr.split("#");
//        Timestamp acceptedCheckTimeStamp = Timestamp.valueOf(arrayInputStr[1]);
//
//        try (Connection connection = dataBaseManager.getConnection()) {
//            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_ACCEPTED);
//            statement.setTimestamp(1, acceptedCheckTimeStamp);
//            statement.executeUpdate();
//        } catch (SQLException e){
//            e.printStackTrace();
//        }
//    }

    @Override
    public boolean deleteById(int id) {
        Session sessionFind = null;
        Session sessionUpdate = null;
        boolean deleted = false;
        try {
            sessionFind = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            Check check = sessionFind.get(Check.class, id);
            check.setDeleted(true);
            sessionFind.close();

            sessionUpdate = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            Transaction transaction = sessionUpdate.beginTransaction();
            sessionUpdate.update(check);
            transaction.commit();

            deleted = true;
        }catch (Exception e){
            System.out.println("Ошибка удаления чека в БД по id.");
            e.printStackTrace();
        }finally {
            if (sessionFind != null && sessionFind.isOpen()) {
                sessionFind.close();
            }
            if (sessionUpdate != null && sessionUpdate.isOpen()) {
                sessionUpdate.close();
            }
        }
        return deleted;
    }
}