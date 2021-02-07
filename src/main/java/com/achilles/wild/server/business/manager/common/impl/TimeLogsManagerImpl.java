package com.achilles.wild.server.business.manager.common.impl;

import com.achilles.wild.server.business.dao.TimeLogsDao;
import com.achilles.wild.server.business.entity.TimeLogs;
import com.achilles.wild.server.business.manager.common.TimeLogsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TimeLogsManagerImpl implements TimeLogsManager {

    @Autowired
    private TimeLogsDao timeLogsDao;

    @Override
    public boolean addLog(TimeLogs log) {

        if (log==null){
            throw new IllegalArgumentException("log can not be null !");
        }

        int insert = timeLogsDao.insertSelective(log);
        if (insert==1){
            return true;
        }

        return false;
    }
}
