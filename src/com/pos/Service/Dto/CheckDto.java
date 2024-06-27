package com.pos.Service.Dto;

import com.pos.Data.Entities.Goods;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.java.Log;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CheckDto {
//    private ArrayList<Goods> goodsList = new ArrayList<>();

    private Long id;
    private String pos;
    private Long cashierId;
    private ArrayList<Goods> goodsDtoList;
    private BigDecimal sum;
    private Date date;
    private Timestamp dateStamp;
    private Boolean deleted;
    private static final String TAG = "logsCheckDto";

    public CheckDto (String checkStr){
        String[] arrayCheckCode = checkStr.split("#");
        this.id=Long.parseLong(arrayCheckCode[1]);
        this.pos=arrayCheckCode[2];
        this.cashierId=Long.parseLong(arrayCheckCode[3]);
        this.goodsDtoList =Goods.createGoodsListFromStr(arrayCheckCode[4]);
        this.sum= new BigDecimal(arrayCheckCode[5]);
        this.dateStamp=Timestamp.valueOf(arrayCheckCode[6]);
        System.out.println("Интерпретирована дата: " + this.dateStamp.toString());
        this.deleted = Boolean.parseBoolean(arrayCheckCode[7]);
    }

    public Boolean isEmpty (){
        if (goodsDtoList.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public void clear(){
        goodsDtoList.clear();
    }

    public void addGoods(int i) {

        Goods currGoods = new Goods();
        currGoods.setGoodsType(i);
        Boolean foundGoodsInCheck = false;

        //если check - создан (повторный выбор товара), проверяем наличие в чека такого же товара
        if (!goodsDtoList.isEmpty()) {

            //поиск в check аналогичного currGoods с typeGoods =i
            for (int x = 0; x < (goodsDtoList.size()); x++) {

                //если есть, то setIncreaseQuantityGoods и замена данной позиции check на currGoods
                if (goodsDtoList.get(x).getGoodsType() == currGoods.getGoodsType()) {
                    Goods compGoods = goodsDtoList.get(x);
                    compGoods.setIncreaseQuantityGoods();
                    goodsDtoList.set(x,compGoods);
                    foundGoodsInCheck = true;
                    break;
                }
            }
        }

        //если check не пустой и не найдено совпадение то добавляем в конце
        if (!foundGoodsInCheck) {
            goodsDtoList.add(currGoods);
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CheckDto checkDto1 = (CheckDto) o;
        return id.equals(checkDto1.id) && pos.equals(checkDto1.pos) && cashierId.equals(checkDto1.cashierId) && goodsDtoList.equals(checkDto1.goodsDtoList) && dateStamp.equals(checkDto1.dateStamp);
    }
    public String getCheckCode() {
        String checkCode = "";
        for (int x = 0; x < goodsDtoList.size(); x++) {
            checkCode = checkCode + goodsDtoList.get(x).getGoodsType() + "\\"
                    + goodsDtoList.get(x).getQuantityGoods();
            if (x != (goodsDtoList.size()-1)){
                checkCode = checkCode + "|";
            }
        }
        return checkCode;
    }
    @Override
    public int hashCode() {
        int hash;
        String str = pos + cashierId.toString() + sum.toString() + dateStamp.toString() + deleted.toString();
        hash = Objects.hash(str);
        return hash;
    }
}
