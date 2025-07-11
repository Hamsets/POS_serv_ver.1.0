package com.pos.Data.Dao.impl;

import com.pos.Data.Connection.DataBaseManager;
import com.pos.Data.Dao.GoodsDao;
import com.pos.Data.Entities.Check;
import com.pos.Data.Entities.Goods;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;

public class GoodsDaoImpl implements GoodsDao {
    private final DataBaseManager dataBaseManager;
    private static final String SQL_FIND_GOODS = "SELECT c.goods_type,c.image_name,c.public_name,c.path_image,c.prize,c.is_active,c.for_pos,c.deleted FROM goods c " +
            "WHERE c.goods_type = ? AND c.for_pos = ? AND c.deleted = false";
    private static final String SQL_FIND_ALL_GOODS = "SELECT c.goods_type,c.image_name,c.public_name,c.path_image,c.prize,c.is_active,c.for_pos,c.deleted FROM goods c " +
            "WHERE c.for_pos = ? AND c.deleted = false";
    public GoodsDaoImpl (DataBaseManager dataBaseManager) {this.dataBaseManager = dataBaseManager;}

    @Override
    public int writeGoods(Goods newGoods) {
        return 0;
    }

    @Override
    public ArrayList<Goods> findGoods(int goodsType, String pos) {
        ArrayList<Goods> goodsArrayList = new ArrayList<>();

        if (goodsType == -1){
            try (Connection connection = dataBaseManager.getConnection()) {
//                PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL_GOODS);
//                preparedStatement.setString(1, pos);
//                ResultSet resultSet = preparedStatement.executeQuery();

                Statement statement = null;
                statement = connection.createStatement();
//                statement.executeUpdate("SELECT c.goods_type,c.image_name,c.public_name,c.path_image,c.prize,c.is_active,c.for_pos,c.deleted FROM goods c " +
//                        "WHERE c.for_pos = 'part' AND c.deleted = false");

                ResultSet resultSet = statement.executeQuery("SELECT * FROM public.goods4");
                System.out.println(resultSet.next());
                System.out.println(resultSet.getString("image_name"));
                connection.close();
//                Goods goods = mapRow(resultSet);
//                goodsArrayList.add(goods);

//                while (resultSet.next()) {
//                    Goods goods = mapRow(resultSet);
//                    goodsArrayList.add(goods);
//                }
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
