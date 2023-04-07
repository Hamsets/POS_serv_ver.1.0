package com.pos.Service.Dto;

import com.pos.Data.Entities.Goods;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CheckDto {

    private Long id;
    private String pos;
    private Long cashierId;
    private ArrayList<Goods> goodsListDto;
    private BigDecimal sum = new BigDecimal(0);
    private Date dateStamp;

    public Boolean isEmpty (){
        if (goodsListDto.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public void clear(){
        goodsListDto.clear();
    }

    public void addGoods(Long i) {

        Goods currGoods = new Goods();
        currGoods.setGoodsType(i);
        Boolean foundGoodsInCheck = false;

        //если check - создан (повторный выбор товара), проверяем наличие в чека такого же товара
        if (!goodsListDto.isEmpty()) {

            //поиск в check аналогичного currGoods с typeGoods =i
            for (int x = 0; x < (goodsListDto.size()); x++) {

                //если есть, то setIncreaseQuantityGoods и замена данной позиции check на currGoods
                if (goodsListDto.get(x).getGoodsType().equals(currGoods.getGoodsType()) ) {
                    Goods compGoods = goodsListDto.get(x);
                    compGoods.setIncreaseQuantityGoods();
                    goodsListDto.set(x,compGoods);
                    foundGoodsInCheck = true;
                    break;
                }
            }
        }

        //если check не пустой и не найдено совпадение то добавляем в конце
        if (!foundGoodsInCheck) {
            goodsListDto.add(currGoods);
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CheckDto checkDto1 = (CheckDto) o;
        return id.equals(checkDto1.id) && pos.equals(checkDto1.pos) && cashierId.equals(checkDto1.cashierId) && goodsListDto.equals(checkDto1.goodsListDto) && dateStamp.equals(checkDto1.dateStamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pos, cashierId, goodsListDto, dateStamp);
    }
}
