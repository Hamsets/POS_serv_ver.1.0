package com.pos.Service.Dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pos.Data.Dao.UserDao;
import com.pos.Data.Dao.impl.UserDaoImpl;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.Objects;

import com.pos.Data.Entities.Role;
import com.pos.Data.Entities.User;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private int id;
    private String firstName;
    private String lastName;
    private String surName;
    private String email;
    private Role role;
    private String password;
    private BigDecimal rating;
    private Boolean deleted;

//    public UserDto (String userStr){
//        String[] arrayUserCode = userStr.split("#");
//        this.email=arrayUserCode[1];
//        this.password=arrayUserCode[2];
//
//    }

    public UserDto (User user){
        this.id = user.getUserId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.surName = user.getSurName();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.password = user.getPassword();
        this.rating = user.getRating();
        this.deleted = user.getDeleted();
    }

    public static String convertToJson (User user) throws NullPointerException{
        String s = "";
        try {
            //писать результат сериализации будем во Writer(StringWriter)
            StringWriter writer = new StringWriter();

            //это объект Jackson, который выполняет сериализацию
            ObjectMapper mapper = new ObjectMapper();

            // сама сериализация: 1-куда, 2-что
            mapper.writeValue(writer, user);

            //преобразовываем все записанное во StringWriter в строку
            s = writer.toString();
        } catch (IOException e){
            System.out.println("Ошибка сериализации User в JSON.");
            e.printStackTrace();
        }
        return s;
    };

    public static User convertFromJson (String s){
        User user = new User ();
        try {
            StringReader reader = new StringReader(s);
            ObjectMapper mapper = new ObjectMapper();
            user = mapper.readValue(reader, User.class);
        }catch (IOException e){
            System.out.println("Ошибка десериализации User из JSON.");
            e.printStackTrace();
        }
        return user;
    }

    //Получаем реального UserDto с реального User из БД, или null, если не найден логин
    public static UserDto getTrueUserByLogin (UserDto userDto){
        UserDao userDao = new UserDaoImpl();
        User user = userDao.compareUser(userDto);
        if (user != null){
            return new UserDto(user);
        } else {
            return null;
        }
    }

    public User getEntity () throws NullPointerException{
        return new User(this.id, this.firstName, this.lastName, this.surName, this.email, this.role, this.password,
                this.rating, this.deleted);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return id==userDto.id
                && firstName.equals(userDto.firstName)
                && lastName.equals(userDto.lastName)
                && surName.equals(userDto.surName)
                && email.equals(userDto.email)
                && role.equals(userDto.role)
                && password.equals(userDto.password)
                && rating.equals(userDto.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, surName, email, role, password, rating);
    }
}
