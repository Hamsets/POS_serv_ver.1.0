package com.pos.Data.Dao;

import com.pos.Data.Entities.Goods;
import java.util.ArrayList;

public interface GoodsDao {
    int writeGoods(Goods newGoods);
    ArrayList<Goods> findGoods(long goodsType, String pos);
    boolean updateGoodsById (long id, Goods newGoods);
    boolean deleteGoodsById (long id);

}
