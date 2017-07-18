package com.upotv.mcs.operlog.dao;

import com.upotv.mcs.core.McsBaseDao;
import com.upotv.mcs.operlog.entity.Log;

/**
 * Created by wow on 2017/7/17.
 */
public interface LogDao extends McsBaseDao {
    int insert(Log log);
}
