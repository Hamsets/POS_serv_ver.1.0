package com.pos.Data.Dao.impl;

import com.pos.Data.Dao.PosDao;
import com.pos.Data.Entities.Pos;
import com.pos.Utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;

public class PosDaoImpl implements PosDao {
    @Override
    public void createPos (Pos pos) {
        Session session = null;
        try{
            session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.save(pos);
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Ошибка создания POS в БД.");
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public Pos findPosById (int id){
        Pos pos = new Pos();
        Session session = null;
        try {
            session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            pos = (Pos) session.load(Pos.class, id);
        } catch (Exception e){
            System.out.println("Ошибка поиска POS в БД по id.");
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return pos;
    }

    @Override
    public boolean updatePos (Pos pos){
        Session session = null;
        boolean updated = false;
        try{
            session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.update(pos);
            transaction.commit();
            updated = true;
        } catch (Exception e){
            System.out.println("Ошибка обновления POS в БД.");
            e.printStackTrace();
        } finally {
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
        Pos pos = HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Pos.class, id);
        pos.setDeleted(true);
        session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(pos);
        transaction.commit();
        deleted = true;
        } catch (Exception e){
            System.out.println("Ошибка удаления POS в БД по id.");
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return deleted;
    }

    @Override
    public ArrayList<Pos> findAllPoses() {
        ArrayList<Pos> arrayPosList = new ArrayList<>();
        Session session = null;
        try {
            session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            arrayPosList= (ArrayList<Pos>) session.createQuery("From Pos").list();
        } catch (Exception e){
            System.out.println("Ошибка получения всех POS из БД.");
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return arrayPosList;
    }
}
