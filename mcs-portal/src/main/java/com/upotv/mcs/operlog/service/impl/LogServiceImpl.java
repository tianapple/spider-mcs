package com.upotv.mcs.operlog.service.impl;

import com.upotv.mcs.operlog.dao.LogDao;
import com.upotv.mcs.operlog.entity.Log;
import com.upotv.mcs.operlog.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wow on 2017/7/17.
 */
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogDao logDao;

    @Override
    public int insert(Log log) {
        return logDao.insert(log);
    }
}
