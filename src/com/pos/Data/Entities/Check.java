package com.pos.Data.Entities;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.*;

import javax.persistence.*;

@JsonAutoDetect
@Entity
@Table (name = "checks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Check {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name ="check_id")
    private int checkId;

    //TODO нужно сделать LASY для FethType - меньшая нагрузка на ресурсы сервера
    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "pos_id")
    private Pos pos;

    //TODO нужно сделать LASY для FethType - меньшая нагрузка на ресурсы сервера
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private BigDecimal sum;

    @Column(name = "date_stamp")
    private Timestamp dateStamp;

    //TODO нужно сделать LASY для FethType - меньшая нагрузка на ресурсы сервера
    @OneToMany (mappedBy = "goodsId", cascade = CascadeType.ALL, fetch = FetchType.EAGER,
            orphanRemoval = true)
    private List<Goods> goodsArrayList;

    @Column
    private Boolean deleted;
}
