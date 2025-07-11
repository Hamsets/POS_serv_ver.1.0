package com.pos.Data.Dao.impl;

import com.pos.Data.Connection.DataBaseManager;
import com.pos.Data.Dao.UserDao;
import com.pos.Data.Entities.User;
import com.pos.Service.Dto.UserDto;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDaoImpl implements UserDao {
    private static final String SQL_INSERT = "INSERT INTO users (first_name,last_name,sur_name," +
            "email, role, password, rating, deleted) VALUES (?,?,?,?,?,?,?,?)";
    private static final String SQL_FIND_BY_ID = "SELECT c.id,c.first_name,c.last_name,c.sur_name,c.email," +
            "c.role,c.password,c.rating,c.deleted FROM users c WHERE c.id=? AND c.deleted = false";
    private static final String SQL_FIND_BY_ROLE = "SELECT c.id,c.first_name,c.last_name,c.sur_name,c.email," +
            "c.role,c.password,c.rating,c.deleted FROM users c WHERE c.role=? AND c.deleted = false";
    private static final String SQL_FIND_BY_RATING = "SELECT c.id,c.first_name,c.last_name,c.sur_name,c.email," +
            "c.role,c.password,c.rating,c.deleted FROM users c WHERE c.rating>? AND c.rating<? AND c.deleted = false";
    private static final String SQL_FIND_REG_USER = "SELECT c.id,c.first_name,c.last_name,c.sur_name,c.email," +
            "c.role,c.password,c.rating,c.deleted FROM users c WHERE c.email=? AND c.password=? AND c.deleted = false";
    private static final String SQL_SELECT_ALL = "SELECT c.id,c.first_name,c.last_name,c.sur_name,c.email," +
            "c.role,c.password,c.rating,c.deleted FROM users c WHERE c.deleted = false";
    private static final String SQL_UPDATE = "UPDATE users SET first_name=?,last_name=?,sur_name=?,email=?," +
            "role=?,password=?,rating=?,deleted=? WHERE id=?";
    private static final String SQL_DELETE = "UPDATE users SET deleted=true WHERE id=?";
    private final DataBaseManager dataBaseManager;
    public UserDaoImpl (DataBaseManager dataBaseManager){this.dataBaseManager=dataBaseManager;}

    @Override
    public void createUser(User user) {
        try (Connection connection = dataBaseManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getSurName());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getRole());
            statement.setString(6, user.getPassword());
            statement.setBigDecimal(7, user.getRating());
            statement.setBoolean(8,user.getDeleted());
            statement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public User findUserById(Long id) {
        try (Connection connection = dataBaseManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return mapRow(resultSet);
        }catch (SQLException e){
            throw new RuntimeException("User not found or SQLException: " + "/n" + e);
        }
    }

    @Override
    public ArrayList<User> findUserByRole(String role) {
        ArrayList<User> userArrayList =  new ArrayList<>();
        try (Connection connection = dataBaseManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ROLE);
            statement.setString(1, role);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = mapRow(resultSet);
                userArrayList.add(user);
            }
        }catch (SQLException e){
            throw new RuntimeException("Users not found or SQLException: " + "/n" + e);
        }
        return userArrayList;
    }

    @Override
    public User compareUser(UserDto userDto) {
        User user;
//        int userId;
        try (Connection connection = dataBaseManager.getConnection()){
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_REG_USER);
            statement.setString(1,userDto.getEmail());
            statement.setString(2,userDto.getPassword());
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            user = mapRow(resultSet);
//            userId=user.getId();
        } catch (SQLException e){
            throw new RuntimeException("User not found or SQLException: " + "/n" + e);
        }
        return user;
    }

    @Override
    public ArrayList<User> findUserByRating(BigDecimal low, BigDecimal high) {
        ArrayList<User> userArrayList =  new ArrayList<>();
        try (Connection connection = dataBaseManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_RATING);
            statement.setBigDecimal(1, low);
            statement.setBigDecimal(2, high);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = mapRow(resultSet);
                userArrayList.add(user);
            }
        }catch (SQLException e){
            throw new RuntimeException("Users not found or SQLException: " + "/n" + e);
        }
        return userArrayList;
    }

    @Override
    public ArrayList<User> findAllUsers() {
        ArrayList<User> userArrayList =  new ArrayList<>();
        try (Connection connection = dataBaseManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = mapRow(resultSet);
                userArrayList.add(user);
            }
        }catch (SQLException e){
            throw new RuntimeException("Users not found or SQLException: " + "/n" + e);
        }
        return userArrayList;
    }

    @Override
    public void updateUser(User user) {
        try (Connection connection = dataBaseManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getSurName());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getRole());
            statement.setString(6, user.getPassword());
            statement.setBigDecimal(7, user.getRating());
            statement.setBoolean(8, user.getDeleted());
            statement.setLong(9, user.getId());
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
        }catch (SQLException e){
            throw new RuntimeException("User not found or SQLException: " + "/n" + e);
        }

    }

    @Override
    public boolean deleteById(Long id) {
        boolean deleted = false;
        try (Connection connection = dataBaseManager.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            deleted = true;
        }catch (SQLException e){
            throw new RuntimeException("User not found or SQLException: " + "/n" + e);
        }
        return deleted;  //FIXME всегда результат будет true
    }

    private User mapRow(ResultSet resultSet) throws SQLException {
        User user = new User(
                resultSet.getInt("id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getString("sur_name"),
                resultSet.getString("email"),
                resultSet.getString("password"),
                resultSet.getString("role"),
                resultSet.getBigDecimal("rating"),
                resultSet.getBoolean("deleted"));
        return user;
    }
}
