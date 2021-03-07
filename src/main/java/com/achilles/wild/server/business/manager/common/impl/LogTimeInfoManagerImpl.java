package com.achilles.wild.server.business.manager.common.impl;

import com.achilles.wild.server.business.dao.common.LogTimeInfoDao;
import com.achilles.wild.server.entity.common.LogTimeInfo;
import com.achilles.wild.server.business.manager.common.LogTimeInfoManager;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogTimeInfoManagerImpl implements LogTimeInfoManager {

    @Autowired
    private LogTimeInfoDao logTimeInfoDao;

    @Override
    public boolean addLog(LogTimeInfo log) {

        if (log==null){
            throw new IllegalArgumentException("log can not be null !");
        }

        int insert = logTimeInfoDao.insertSelective(log);
        if (insert==1){
            return true;
        }

        return false;
    }

    @Override
    public boolean addLogs(List<LogTimeInfo> logTimeInfoList) {

        if (CollectionUtils.isEmpty(logTimeInfoList)){
            throw new IllegalArgumentException("list can not be empty !");
        }

        logTimeInfoList.stream().forEach(logTimeInfo -> {
            if (logTimeInfo.getClz()==null){
                logTimeInfo.setClz("0");
            }
            if (logTimeInfo.getMethod()==null){
                logTimeInfo.setMethod("0");
            }
        });

        int insert = logTimeInfoDao.batchInsert(logTimeInfoList);
        if (insert==1){
            return true;
        }

        return false;
    }
}
