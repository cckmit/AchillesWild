package com.achilles.wild.server.business.manager.common.impl;

import com.achilles.wild.server.business.dao.LogFilterDao;
import com.achilles.wild.server.business.manager.common.LogFilterManager;
import com.achilles.wild.server.entity.common.LogFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogFilterManagerImpl implements LogFilterManager {

    @Autowired
    private LogFilterDao logFilterDao;

    @Override
    public boolean addLog(LogFilter logFilter) {
        if (logFilter == null){
            throw new IllegalArgumentException("filterLogs can not be null !");
        }

        int insert = logFilterDao.insertSelective(logFilter);
        if (insert==1){
            return true;
        }

        return false;
    }
}
