package com.pos.Data.Entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Goods {

    private int goodsType;
    private int quantityGoods;
    private String imageName;
    private String publicName;
    private String pathImage;
    private BigDecimal prize = new BigDecimal(0);
    private Boolean isActive;
    private String forPos;
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
        Goods goods = new Goods(Integer.parseInt(arrayGoodsQuantity[0]), Integer.parseInt(arrayGoodsQuantity[1]),
                null,null,null,null,null,null,null);
        return goods;
    }

}
