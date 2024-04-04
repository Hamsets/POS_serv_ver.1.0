package com.pos.Data.Entities;

import java.math.BigDecimal;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private int id;
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
        User user = (User) o;
        return id==user.id && firstName.equals(user.firstName) && lastName.equals(user.lastName)
                && surName.equals(user.surName) && email.equals(user.email) && password.equals(user.password)
                && role.equals(user.role) && rating.equals(user.rating) && deleted.equals(user.deleted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, surName, email, password, role, rating, deleted);
    }
}
