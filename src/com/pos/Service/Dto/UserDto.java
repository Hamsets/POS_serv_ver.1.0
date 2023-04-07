package com.pos.Service.Dto;

import java.math.BigDecimal;
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
    private Long id;
    private String firstName;
    private String lastName;
    private String surName;
    private String email;
    private String password;
    private String role;
    private BigDecimal rating;
    private Boolean deleted;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return id.equals(userDto.id) && firstName.equals(userDto.firstName) && lastName.equals(userDto.lastName) && surName.equals(userDto.surName) && email.equals(userDto.email) && password.equals(userDto.password) && role.equals(userDto.role) && rating.equals(userDto.rating) && deleted.equals(userDto.deleted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, surName, email, password, role, rating, deleted);
    }
}
