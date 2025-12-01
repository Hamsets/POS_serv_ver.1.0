package com.pos.Service.Dto;

import com.pos.Data.Entities.Pos;
import com.pos.Data.Entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PosDto {

    private int id;
    private String publicName;
    private String address;
    private List<User> usersIds;
    private Boolean deleted;

    public PosDto (Pos pos) {
        this.id = pos.getPosId();
        this.publicName = pos.getPublicName();
        this.address = pos.getAddress();
        this.deleted = pos.getDeleted();
    }

    public Pos getEntity (){
        return new Pos(this.id, this.publicName, this.address, this.deleted);
    }
}
