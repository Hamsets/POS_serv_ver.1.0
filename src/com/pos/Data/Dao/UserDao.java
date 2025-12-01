package com.pos.Data.Dao;

import com.pos.Data.Entities.User;
import com.pos.Service.Dto.UserDto;
import java.math.BigDecimal;
import java.util.ArrayList;

public interface UserDao {

    boolean createUser(User user);
    User findUserById(int id);
    ArrayList<User> findUserByRole(String role);
    ArrayList<User> findUserByRating(BigDecimal low, BigDecimal high);
    ArrayList<User> findAllUsers();
    boolean updateUser(User user);
    boolean deleteById(int id);
    User compareUser(UserDto userDto);

}
