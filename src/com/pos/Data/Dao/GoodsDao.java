package com.pos.Data.Dao;

import com.pos.Data.Entities.Goods;
import com.pos.Data.Entities.Pos;

import java.util.ArrayList;
import java.util.List;

public interface GoodsDao {
    boolean createGoods(Goods newGoods);
    List<Goods> findAllGoods();
    boolean updateGoods(Goods goods);
    boolean deleteGoodsById (int id);
    Goods findGoodsById (int id);

}
