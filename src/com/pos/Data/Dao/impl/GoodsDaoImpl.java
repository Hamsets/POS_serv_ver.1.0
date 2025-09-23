package com.pos.Data.Dao.impl;

import com.pos.Data.Connection.DataBaseManager;
import com.pos.Data.Dao.GoodsDao;
import com.pos.Data.Entities.Goods;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;

public class GoodsDaoImpl implements GoodsDao {
    private final DataBaseManager dataBaseManager;
    private static final String SQL_FIND_GOODS = "SELECT c.goods_type,c.image_name,c.public_name,c.path_image,c.prize,"
            + "c.is_active,c.for_pos,c.deleted FROM goods c WHERE c.goods_type = ? "
            + "AND c.for_pos = ? "
            + "AND c.deleted = false";
    private static final String SQL_FIND_ALL_GOODS_BU_POS = "SELECT c.goods_type,c.image_name,c.public_name," +
            "c.path_image,c.prize,c.is_active,c.for_pos,c.deleted FROM goods c " +
            "WHERE c.for_pos = ? "
            + "AND c.deleted = false";

    private static final String SQL_FIND_ALL_GOODS_BY_ID = "SELECT c.goods_type,c.image_name,c.public_name,c.path_image," +
            "c.prize,c.is_active,c.for_pos,c.deleted FROM goods c "
            + "WHERE c.goods_type = ? ";

    public GoodsDaoImpl (DataBaseManager dataBaseManager) {this.dataBaseManager = dataBaseManager;}

    @Override
    public int writeGoods(Goods newGoods) {
        return 0;
    }

    @Override
    public Goods findGoodsById (int id){
        Goods goods = new Goods();


        try (Connection connection = dataBaseManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL_GOODS_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Goods g = mapRow(resultSet);
                goods = g;
            }
        } catch (SQLException e) {
            System.out.println("Ошибка извлечения товара по типу из БД.");
            System.out.println(e.toString());
            goods = new Goods();
        }


        return goods;
    }


    @Override
    public ArrayList<Goods> findGoods(int goodsType, String pos) {
        ArrayList<Goods> goodsArrayList = new ArrayList<>();

        //Если пришел запрос -1, то отображаем все товары, если пришел id - то только этот товар

        if (goodsType == -1){
            try (Connection connection = dataBaseManager.getConnection()) {
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL_GOODS_BU_POS);
                preparedStatement.setString(1, pos);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Goods goods = mapRow(resultSet);
                    goodsArrayList.add(goods);
                }
            } catch (SQLException e) {
                System.out.println(e.toString());
                goodsArrayList.add(new Goods());
            }
        } else {
            try (Connection connection = dataBaseManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_GOODS);
            preparedStatement.setLong(1, goodsType);
            preparedStatement.setString(2, pos);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Goods goods = mapRow(resultSet);
                goodsArrayList.add(goods);
            }
        } catch (SQLException e) {
            System.out.println("Ошибка извлечения товара по типу из БД.");
            return null;
        }

        }

        return goodsArrayList;
    }


    private Goods mapRow(ResultSet resultSet) throws SQLException {

        return new Goods(
                resultSet.getInt("goods_type"),
                1,
                resultSet.getString("image_name"),
                resultSet.getString("public_name"),
                resultSet.getString("path_image"),
                new BigDecimal(resultSet.getString("prize")),
                resultSet.getBoolean("is_active"),
                resultSet.getString("for_pos"),
                resultSet.getBoolean("deleted"));
    }



    @Override
    public boolean updateGoodsById(int id, Goods newGoods) {
        return false;
    }

    @Override
    public boolean deleteGoodsById(int id) {
        return false;
    }
}
