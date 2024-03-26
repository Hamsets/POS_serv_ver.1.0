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
    private ArrayList<Goods> goodsList = new ArrayList<>();
    private BigDecimal sum = new BigDecimal(0);
    private Timestamp dateStamp;
    private Boolean deleted;
    public String getCheckCode() {
        String checkCode = "";
        for (int x = 0; x < goodsList.size(); x++) {
            checkCode = checkCode + goodsList.get(x).getGoodsType() + "\\"
                    + goodsList.get(x).getQuantityGoods();
            if (x != (goodsList.size()-1)){
                checkCode = checkCode + "|";
            }
        }
        return checkCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Check check = (Check) o;
        return pos.equals(check.pos) && cashierId.equals(check.cashierId) && goodsList.equals(check.goodsList) && sum.equals(check.sum) && dateStamp.equals(check.dateStamp) && deleted.equals(check.deleted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pos, cashierId, goodsList, sum, dateStamp, deleted);
    }
}
