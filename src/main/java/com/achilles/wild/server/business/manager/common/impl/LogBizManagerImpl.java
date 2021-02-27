package com.achilles.wild.server.business.manager.common.impl;

import com.achilles.wild.server.business.dao.LogBizDao;
import com.achilles.wild.server.entity.common.LogBiz;
import com.achilles.wild.server.business.manager.common.LogBizManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogBizManagerImpl implements LogBizManager {

    @Autowired
    private LogBizDao logBizDao;

    @Override
    public boolean addLog(LogBiz log) {

        if (log==null){
            throw new IllegalArgumentException("log can not be null !");
        }

        int insert = logBizDao.insertSelective(log);
        if (insert==1){
            return true;
        }

        return false;
    }
}
