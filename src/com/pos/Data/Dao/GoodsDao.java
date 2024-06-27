package com.pos.Data.Dao;

import com.pos.Data.Entities.Goods;
import java.util.ArrayList;

public interface GoodsDao {
    int writeGoods(Goods newGoods);//FIXME написать GoodsDto и внести сюда
    ArrayList<Goods> findGoods(int goodsType, String pos);
    boolean updateGoodsById (int id, Goods newGoods);
    boolean deleteGoodsById (int id);

}
