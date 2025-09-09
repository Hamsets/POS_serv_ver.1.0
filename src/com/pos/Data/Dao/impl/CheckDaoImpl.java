package com.pos.Data.Dao.impl;

import com.pos.Data.Connection.DataBaseManager;
import com.pos.Data.Dao.CheckDao;
import com.pos.Data.Entities.Check;
import com.pos.Data.Entities.Goods;
import com.pos.Service.Dto.CheckDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class CheckDaoImpl implements CheckDao {
    private static final String SQL_TAKE_SUM_BY_DATE = "SELECT c.id,c.pos,c.cashier_id,c.check_code,c.sum,c.date_stamp,c.deleted FROM checks c " +
            "WHERE ? <= date_stamp AND c.date_stamp <= ? AND c.deleted = false";
    private static final String SQL_INSERT_NOT_ACCEPTED = "INSERT INTO checks (pos,cashier_id,check_code,sum,date_stamp,deleted) " +
            "VALUES (?,?,?,?,?,?)";
    private static final String SQL_FIND_BY_ID = "SELECT c.id,c.pos,c.cashier_id,c.check_code,c.sum,c.date_stamp,c.deleted FROM checks c " +
            "WHERE c.id=?";
    private static final String SQL_FIND_BY_POS = "SELECT c.id,c.pos,c.cashier_id,c.check_code,c.sum,c.date_stamp,c.deleted FROM checks c " +
            "WHERE c.pos=?";
    private static final String SQL_UPDATE = "UPDATE checks SET pos=?,cashier_id=?,check_code=?,sum=?,date_stamp=?,deleted=? " +
            "WHERE id=?";
    private static final String SQL_UPDATE_ACCEPTED = "UPDATE checks SET deleted = false WHERE date_stamp=?";
    private static final String SQL_DELETE = "UPDATE checks SET deleted=true WHERE id=?";
    private final DataBaseManager dataBaseManager;

    public CheckDaoImpl(DataBaseManager dataBaseManager) {this.dataBaseManager = dataBaseManager;}

    @Override
    public int writeCheck(CheckDto checkDto) {
        try (Connection connection = dataBaseManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT_NOT_ACCEPTED);
            statement.setString(1, checkDto.getPos());
            statement.setLong(2, checkDto.getCashierId());
            statement.setString(3, checkDto.getCheckCode());
            statement.setBigDecimal(4, checkDto.getSum());
            statement.setTimestamp(5, checkDto.getDateStamp());
            statement.setBoolean(6, checkDto.getDeleted());
            statement.executeUpdate();
            return checkDto.hashCode();
        } catch (SQLException e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public Check findCheckById(Long id) {
        try (Connection connection = dataBaseManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return mapRow(resultSet);
        }catch (SQLException e){
            throw new RuntimeException("Check not found or SQLException: " + "/n" + e);
        }
    }

    @Override
    public ArrayList<Check> findCheckByDate(Timestamp startDate, Timestamp endDate) {
        ArrayList<Check> checkArrayList = new ArrayList<>();

        try (Connection connection = dataBaseManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_TAKE_SUM_BY_DATE);
            preparedStatement.setTimestamp(1, startDate);
            preparedStatement.setTimestamp(2, endDate);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Check check = mapRow(resultSet);
                checkArrayList.add(check);
            }
        } catch (SQLException e) {
            System.out.println("Чеки по дате не найдены.");
            return null;
        }
        return checkArrayList;
    }

    @Override
    public Check findCheckByPos(String idPos) {
        return null;
    }

    @Override
    public void updateCheckAccepted(String inputStr) {
        String[] arrayInputStr = inputStr.split("#");
        Timestamp acceptedCheckTimeStamp = Timestamp.valueOf(arrayInputStr[1]);

        try (Connection connection = dataBaseManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_ACCEPTED);
            statement.setTimestamp(1, acceptedCheckTimeStamp);
            statement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = dataBaseManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE);
            statement.setLong(1, id);
            statement.executeUpdate();
            return true;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    private Check mapRow(ResultSet resultSet) throws SQLException {

        return new Check(
                resultSet.getLong("id"),
                resultSet.getString("pos"),
                resultSet.getLong("cashier_id"),
                Goods.createGoodsListFromStr(resultSet.getString("check_code")),
                resultSet.getBigDecimal("sum"),
                resultSet.getTimestamp("date_stamp"),
                resultSet.getBoolean("deleted"));
    }
}