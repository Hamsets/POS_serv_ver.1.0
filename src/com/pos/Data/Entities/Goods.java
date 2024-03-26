package com.pos.Data.Entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Goods {

    private long goodsType;
    private int quantityGoods;
    private String imageName;
    private String publicName;
    private BigDecimal prize = new BigDecimal(0);
    private Boolean isActive;
    private Boolean deleted;

    public void setIncreaseQuantityGoods() {
        quantityGoods++;
    }
    public void setDecreaseQuantityGoods () {
        quantityGoods--;
    }

    public static ArrayList<Goods> createGoodsListFromStr(String checkCode){
        ArrayList<Goods> goodsList = new ArrayList<>();
        String[] arrayGoodsStr = checkCode.split("\\|");
        for (String s: arrayGoodsStr){
            goodsList.add(createGoodsFromShortString(s));
        }
        return goodsList;
    }

    private static Goods createGoodsFromShortString(String strGoodsQuantity) {
        String[] arrayGoodsQuantity = strGoodsQuantity.split("\\\\");
        Goods goods = new Goods(Long.parseLong(arrayGoodsQuantity[0]), Integer.parseInt(arrayGoodsQuantity[1]),
                null,null,null,null,null);
        return goods;
    }

}
