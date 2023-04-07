package com.pos.Data.Entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Check {

    private Long id;
    private String pos;
    private Long cashierId;
    private ArrayList<Goods> goodsList;
    private BigDecimal sum = new BigDecimal(0);
    private Date dateStamp;

    public Boolean isEmpty (){
        if (goodsList.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public void clear(){
        goodsList.clear();
    }

    public void addGoods(Long i) {

        Goods currGoods = new Goods();
        currGoods.setGoodsType(i);
        Boolean foundGoodsInCheck = false;

        //если check - создан (повторный выбор товара), проверяем наличие в чека такого же товара
        if (!goodsList.isEmpty()) {

            //поиск в check аналогичного currGoods с typeGoods =i
            for (int x = 0; x < (goodsList.size()); x++) {

                //если есть, то setIncreaseQuantityGoods и замена данной позиции check на currGoods
                if (goodsList.get(x).getGoodsType().equals(currGoods.getGoodsType()) ) {
                    Goods compGoods = goodsList.get(x);
                    compGoods.setIncreaseQuantityGoods();
                    goodsList.set(x,compGoods);
                    foundGoodsInCheck = true;
                    break;
                }
            }
        }

        //если check не пустой и не найдено совпадение то добавляем в конце
        if (!foundGoodsInCheck) {
            goodsList.add(currGoods);
        }

    }
}
