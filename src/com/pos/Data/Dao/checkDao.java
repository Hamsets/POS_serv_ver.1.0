package com.pos.Data.Dao;

import com.pos.Data.Entities.Check;

public interface checkDao {
    void createCheck (Check check);
    Check findCheckById (Long id);
    Check findCheckByPos (String idPos);
    void updateCheck (Check check);
    boolean deleteById (Long id);

}
