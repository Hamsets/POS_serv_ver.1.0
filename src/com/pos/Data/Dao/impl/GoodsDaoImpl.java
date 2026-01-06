package com.pos.Data.Dao.impl;

import com.pos.Data.Dao.GoodsDao;
import com.pos.Data.Entities.Goods;
import com.pos.Data.Entities.Pos;
import com.pos.Data.Entities.User;
import com.pos.Utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GoodsDaoImpl implements GoodsDao {

    @Override
    public boolean createGoods(Goods goods) {
        Session session = null;
        boolean created = false;
        try {
            session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.save(goods);
            transaction.commit();
            created = true;
        } catch (Exception e){
            System.out.println("Ошибка создания товара в БД.");
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return created;
    }

    //Если пришел запрос не -1, то отображаем только этот товар
    @Override
    public Goods findGoodsById (int id){
        Goods goods = new Goods();
        Session session = null;
        try {
            session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            goods = session.load(Goods.class, id);
        }catch (Exception e){
            System.out.println("Ошибка поиска товара в БД по id.");
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return goods;
    }

    //Если пришел запрос -1, то отображаем все товары
    //TODO отобразить только для текущей POS
    @Override
//    @Transactional
    public List<Goods> findAllGoods(int posId) {
        List<Goods> goodsList = new ArrayList<>();
        Session session = null;
        try {
            session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            goodsList = (List<Goods>) session.createQuery("From Goods Where for_pos = " + posId).list();
//            goodsList.add(new Goods());
        } catch (Exception e) {
            System.out.println("Ошибка получения всех Goods из БД.");
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return goodsList;
    }

    public boolean updateGoods(Goods goods) {
        Session session = null;
        boolean updated = false;
        try {
            session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.update(goods);
            transaction.commit();
            updated = true;
        }catch (Exception e){
            System.out.println("Ошибка обновления товара в БД.");
            e.printStackTrace();
        }finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return updated;
    }

    @Override
    public boolean deleteGoodsById(int id) {
        Session session = null;
        boolean deleted = false;
        try {
            Goods goods = HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Goods.class, id);
            goods.setDeleted(true);
            session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.update(goods);
            transaction.commit();
            deleted = true;
        }catch (Exception e){
            System.out.println("Ошибка удаления товара в БД по id.");
            e.printStackTrace();
        }finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return deleted;
    }
}
