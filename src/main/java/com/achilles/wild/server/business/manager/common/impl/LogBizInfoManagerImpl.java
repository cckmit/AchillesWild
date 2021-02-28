package com.achilles.wild.server.business.manager.common.impl;

import com.achilles.wild.server.business.dao.common.LogBizDao;
import com.achilles.wild.server.entity.common.LogBizInfo;
import com.achilles.wild.server.business.manager.common.LogBizInfoManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogBizInfoManagerImpl implements LogBizInfoManager {

    @Autowired
    private LogBizDao logBizDao;

    @Override
    public boolean addLog(LogBizInfo log) {

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
