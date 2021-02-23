package com.achilles.wild.server.business.manager.common.impl;

import com.achilles.wild.server.business.dao.FilterLogsDao;
import com.achilles.wild.server.business.manager.common.FilterLogsManager;
import com.achilles.wild.server.entity.FilterLogs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FilterLogsManagerImpl implements FilterLogsManager {

    @Autowired
    private FilterLogsDao filterLogsDao;

    @Override
    public boolean addFilterLog(FilterLogs filterLogs) {
        if (filterLogs == null){
            throw new IllegalArgumentException("filterLogs can not be null !");
        }

        int insert = filterLogsDao.insertSelective(filterLogs);
        if (insert==1){
            return true;
        }

        return false;
    }
}
