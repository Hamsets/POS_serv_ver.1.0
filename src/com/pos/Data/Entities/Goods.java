package com.pos.Data.Entities;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.pos.Data.Connection.DataBaseManager;
import com.pos.Data.Dao.GoodsDao;
import com.pos.Data.Dao.impl.GoodsDaoImpl;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    public static ArrayList<Goods> createGoodsListFromStr(String checkCode){
        ArrayList<Goods> goodsList = new ArrayList<>();
        String[] arrayGoodsStr = checkCode.split("\\|");
        for (String s: arrayGoodsStr){
            goodsList.add(createGoodsFromCheckCode(s));
        }
        return goodsList;
    }

    private static Goods createGoodsFromCheckCode(String goodsStr) {
        String[] arrayGoodsStr = goodsStr.split("\\\\");
        Goods goods;
        if (arrayGoodsStr.length == 2){
            goods = createRealGoods(arrayGoodsStr);
        } else if (arrayGoodsStr.length > 2) {
            goods = new Goods(Integer.parseInt(arrayGoodsStr[0]), Integer.parseInt(arrayGoodsStr[1]),
                    arrayGoodsStr[2],arrayGoodsStr[3],arrayGoodsStr[4],new BigDecimal(arrayGoodsStr[5]),
                    Boolean.parseBoolean(arrayGoodsStr[6]),arrayGoodsStr[7],Boolean.parseBoolean(arrayGoodsStr[8]));
        } else {
            return null;
        }

        return goods;
    }

    private static Goods createRealGoods (String[] arrayGoodsStr){
        GoodsDao goodsDao = new GoodsDaoImpl(new DataBaseManager());
        Goods goods;

        goods = goodsDao.findGoodsById(Integer.parseInt(arrayGoodsStr[0]));
        goods.setQuantityGoods(Integer.parseInt(arrayGoodsStr[1]));
        return goods;
    }

    public String toString() {
        return goodsType + "/"
                + quantityGoods + "/"
                + imageName + "/"
                + publicName + "/"
                + pathImage + "/"
                + prize + "/"
                + isActive + "/"
                + forPos + "/"
                + deleted;
    }
}
