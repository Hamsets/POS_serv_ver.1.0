package com.pos.Data.Dao;

import com.pos.Data.Entities.Pos;

import java.util.ArrayList;

public interface PosDao {
    void createPos (Pos pos);
    Pos findPosById (int id);
    boolean updatePos (Pos pos);
    boolean deleteById (int id);
    ArrayList<Pos> findAllPoses ();
}
