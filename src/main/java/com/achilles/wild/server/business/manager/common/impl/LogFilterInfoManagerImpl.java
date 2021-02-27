package com.achilles.wild.server.business.manager.common.impl;

import com.achilles.wild.server.business.dao.LogFilterInfoDao;
import com.achilles.wild.server.business.manager.common.LogFilterInfoManager;
import com.achilles.wild.server.entity.common.LogFilterInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogFilterInfoManagerImpl implements LogFilterInfoManager {

    @Autowired
    private LogFilterInfoDao logFilterInfoDao;

    @Override
    public boolean addLog(LogFilterInfo logFilterInfo) {
        if (logFilterInfo == null){
            throw new IllegalArgumentException("filterLogs can not be null !");
        }

        int insert = logFilterInfoDao.insertSelective(logFilterInfo);
        if (insert==1){
            return true;
        }

        return false;
    }
}
