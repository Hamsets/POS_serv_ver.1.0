package com.pos.Data.Entities;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Goods {

    private Long goodsType;
    private int quantity;
    private String imageName;
    private String publicName;
    private BigDecimal prize = new BigDecimal(0);
    private Boolean isActive;
    private Boolean deleted;

    public void setIncreaseQuantityGoods() {
        quantity++;
    }
    public void setDecreaseQuantityGoods () {
        quantity--;
    }

}
