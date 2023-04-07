package com.pos.Data.Dao;

import com.pos.Data.Entities.User;
import java.math.BigDecimal;
import java.util.ArrayList;

public interface UserDao {

    void createUser(User user);
    User findUserById(Long id);
    ArrayList<User> findUserByRole(String role);
    ArrayList<User> findUserByRating(BigDecimal low, BigDecimal high);
    ArrayList<User> findAllUsers();
    void updateUser(User user);
    boolean deleteById(Long id);

}
