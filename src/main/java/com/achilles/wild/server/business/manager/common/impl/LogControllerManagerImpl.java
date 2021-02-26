package com.achilles.wild.server.business.manager.common.impl;

import com.achilles.wild.server.business.dao.LogControllerDao;
import com.achilles.wild.server.entity.common.LogController;
import com.achilles.wild.server.business.manager.common.LogControllerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogControllerManagerImpl implements LogControllerManager {

    @Autowired
    private LogControllerDao logControllerDao;

    @Override
    public boolean addLog(LogController log) {

        if (log==null){
            throw new IllegalArgumentException("log can not be null !");
        }

        int insert = logControllerDao.insertSelective(log);
        if (insert==1){
            return true;
        }

        return false;
    }
}
