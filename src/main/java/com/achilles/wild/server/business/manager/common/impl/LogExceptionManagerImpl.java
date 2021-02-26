package com.achilles.wild.server.business.manager.common.impl;

import com.achilles.wild.server.business.dao.LogExceptionDao;
import com.achilles.wild.server.entity.common.LogException;
import com.achilles.wild.server.business.manager.common.LogExceptionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogExceptionManagerImpl implements LogExceptionManager {

    @Autowired
    private LogExceptionDao logExceptionDao;

    @Override
    public boolean addLog(LogException log) {
        if (log==null){
            throw new IllegalArgumentException("log can not be null !");
        }

        int insert = logExceptionDao.insertSelective(log);
        if (insert==1){
            return true;
        }

        return false;
    }
}
