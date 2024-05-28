package com.pos.Data.Dao;

import com.pos.Data.Entities.Check;
import com.pos.Service.Dto.CheckDto;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;

public interface CheckDao {
    int writeCheck(CheckDto checkDto);
    Check findCheckById (Long id);
    Check findCheckByPos (String idPos);
    ArrayList<Check> findCheckByDate(Timestamp startDate, Timestamp endDate);
    void updateCheckAccepted(String inputStr);
    boolean deleteById (Long id);

}
