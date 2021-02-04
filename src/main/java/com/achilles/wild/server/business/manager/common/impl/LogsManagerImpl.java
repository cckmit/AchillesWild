package com.achilles.wild.server.business.manager.common.impl;

import com.achilles.wild.server.business.dao.LogsDao;
import com.achilles.wild.server.business.entity.Logs;
import com.achilles.wild.server.business.manager.common.LogsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogsManagerImpl implements LogsManager {

    @Autowired
    private LogsDao logsDao;

    @Override
    public boolean addLog(Logs log) {

        if (log==null){
            throw new IllegalArgumentException("log can not be null !");
        }

        int insert = logsDao.insertSelective(log);
        if (insert==1){
            return true;
        }

        return false;
    }
}
