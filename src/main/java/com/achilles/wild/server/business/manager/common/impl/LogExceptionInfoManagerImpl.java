package com.achilles.wild.server.business.manager.common.impl;

import com.achilles.wild.server.business.dao.common.LogExceptionInfoDao;
import com.achilles.wild.server.entity.common.LogExceptionInfo;
import com.achilles.wild.server.business.manager.common.LogExceptionInfoManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogExceptionInfoManagerImpl implements LogExceptionInfoManager {

    @Autowired
    private LogExceptionInfoDao logExceptionInfoDao;

    @Override
    public boolean addLog(LogExceptionInfo log) {
        if (log==null){
            throw new IllegalArgumentException("log can not be null !");
        }

        int insert = logExceptionInfoDao.insertSelective(log);
        if (insert==1){
            return true;
        }

        return false;
    }
}
