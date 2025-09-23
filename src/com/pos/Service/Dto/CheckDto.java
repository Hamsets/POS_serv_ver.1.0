package com.pos.Service.Dto;

import com.pos.Data.Entities.Goods;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.java.Log;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckDto {

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
                    + goodsDtoList.get(x).getQuantityGoods() + "\\"
                    + goodsDtoList.get(x).getImageName() + "\\"
                    + goodsDtoList.get(x).getPublicName() + "\\"
                    + goodsDtoList.get(x).getPathImage() + "\\"
                    + goodsDtoList.get(x).getPrize().toString() + "\\"
                    + goodsDtoList.get(x).getIsActive().toString() + "\\"
                    + goodsDtoList.get(x).getForPos() + "\\"
                    + goodsDtoList.get(x).getDeleted().toString();
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
