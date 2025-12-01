package com.pos.Data.Entities;

import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.*;
import javax.persistence.*;

@Entity
@Table (name = "users")

@JsonAutoDetect
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "user_id")
    private int userId;

    @Column (name = "first_name")
    private String firstName;

    @Column (name = "last_name")
    private String lastName;

    @Column (name = "sur_name")
    private String surName;

    @Column
    private String email;

    @Column
    private Role role;

    @Column
    private String password;

    @Column
    private BigDecimal rating;

    @Column
    private Boolean deleted;
}
