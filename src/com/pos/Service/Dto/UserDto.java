package com.pos.Service.Dto;

import com.pos.Data.Entities.Goods;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private int id;
    private String firstName;
    private String lastName;
    private String surName;
    private String email;
    private String role;
    private String password;
    private BigDecimal rating;
    private Boolean deleted;
    public UserDto (String userStr){
        String[] arrayUserCode = userStr.split("#");
        this.email=arrayUserCode[1];
        this.password=arrayUserCode[2];

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
