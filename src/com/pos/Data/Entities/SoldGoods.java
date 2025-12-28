package com.pos.Data.Entities;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@JsonAutoDetect
@Entity
@Table(name = "sold_goods")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SoldGoods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "sold_goods_id")
    private int soldGoodsId;

    @Column (name = "goods_id")
    private int goodsId;

    @Column (name = "quantity")
    private int quantity;

    @Column (name = "image_name")
    private String imageName;

    @Column (name = "public_name")
    private String publicName;

    @Column (name = "path_image")
    private String pathImage;

    @Column
    private BigDecimal prize;

    //TODO нужно сделать LASY для FethType - меньшая нагрузка на ресурсы сервера
//    @ManyToOne (fetch = FetchType.EAGER)
//    @JoinColumn (name = "check_id")
//    private Check check;
//    @Column (name = "check_id")
//    private int checkId;
}
