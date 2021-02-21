package com.achilles.wild.server.business.manager.common.impl;

import com.achilles.wild.server.business.dao.ExceptionLogsDao;
import com.achilles.wild.server.entity.ExceptionLogs;
import com.achilles.wild.server.business.manager.common.ExceptionLogsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExceptionLogsManagerImpl implements ExceptionLogsManager {

    @Autowired
    private ExceptionLogsDao exceptionLogsDao;

    @Override
    public boolean addLog(ExceptionLogs log) {
        if (log==null){
            throw new IllegalArgumentException("log can not be null !");
        }

        int insert = exceptionLogsDao.insertSelective(log);
        if (insert==1){
            return true;
        }

        return false;
    }
}
