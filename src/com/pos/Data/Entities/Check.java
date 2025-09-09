package com.pos.Data.Entities;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Objects;
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
    private ArrayList<Goods> goodsArrayList = new ArrayList<>();
    private BigDecimal sum = new BigDecimal(0);
    private Timestamp dateStamp;
    private Boolean deleted;

    public String getCheckCode() {
        String checkCode = "";
        for (int x = 0; x < goodsArrayList.size(); x++) {
            checkCode = checkCode + goodsArrayList.get(x).getGoodsType() + "\\"
                    + goodsArrayList.get(x).getQuantityGoods();
            if (x != (goodsArrayList.size()-1)){
                checkCode = checkCode + "|";
            }
        }
        return checkCode;
    }

    @Override
    public String  toString() {

        return id.toString() + "|"
                + pos + "|"
                + cashierId.toString() + "|"
                + getGoodsArrayListStr() + "|"
                + sum.toString() + "|"
                + dateStamp.toString() + "|"
                + deleted.toString();
    }

    private String getGoodsArrayListStr (){
        String fullGoodsStr = "";
        String cellGoodsCode = "";
        for (Goods goods : goodsArrayList) {
            cellGoodsCode = goods.getGoodsType() + "/" + goods.getQuantityGoods();
            fullGoodsStr = cellGoodsCode + "\\";
        }
        return fullGoodsStr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Check check = (Check) o;
        return pos.equals(check.pos) && cashierId.equals(check.cashierId) && goodsArrayList.equals(check.goodsArrayList)
                && sum.equals(check.sum) && dateStamp.equals(check.dateStamp) && deleted.equals(check.deleted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pos, cashierId, goodsArrayList, sum, dateStamp, deleted);
    }
}
