package com.pos.Data.Entities;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@JsonAutoDetect
@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Goods {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "goods_id")
    private int goodsId;

    @Column (name = "quantity")
    private int quantityGoods;

    @Column (name = "image_name")
    private String imageName;

    @Column (name = "public_name")
    private String publicName;

    @Column (name = "path_image")
    private String pathImage;

    @Column
    private BigDecimal prize;

    @Column (name ="is_active")
    private Boolean isActive;

    //TODO нужно сделать LASY для FethType - меньшая нагрузка на ресурсы сервера
    @OneToMany (mappedBy = "posId", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Pos> posIds;

    @Column
    private Boolean deleted;
}
