package com.pos.Data.Dao;

import com.pos.Data.Entities.Check;
import com.pos.Data.Entities.Pos;
import com.pos.Service.Dto.CheckDto;

import java.sql.Timestamp;
import java.util.ArrayList;

public interface CheckDao {
    boolean createCheck(Check check);
    Check findCheckById (int id);
    ArrayList<Check> findCheckByPos (Pos pos);
    ArrayList<Check> findCheckByDate(Timestamp startDate, Timestamp endDate, int posId);
//    void updateCheckAccepted(String inputStr);
    boolean deleteById (int id);

}
