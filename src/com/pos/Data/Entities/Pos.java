package com.pos.Data.Entities;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@JsonAutoDetect
@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "pos_id")
    private int posId;

    @Column (name = "public_name")
    private String publicName;

    @Column
    private String address;

//    @OneToMany (mappedBy = "userId",cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<User> usersIds;

    @Column
    private Boolean deleted;
}
