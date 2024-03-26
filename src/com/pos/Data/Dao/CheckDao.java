package com.pos.Data.Dao;

import com.pos.Data.Entities.Check;
import com.pos.Service.Dto.CheckDto;

public interface CheckDao {
    int writeCheck(CheckDto checkDto);
    Check findCheckById (Long id);
    Check findCheckByPos (String idPos);
    void updateCheckAccepted(String inputStr);
    boolean deleteById (Long id);

}
