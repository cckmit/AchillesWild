package com.achilles.wild.server.business.manager.account.impl;

import com.achilles.wild.server.business.dao.common.ConfigParamsDao;
import com.achilles.wild.server.entity.common.ConfigParams;
import com.achilles.wild.server.business.manager.account.ParamsManager;
import com.achilles.wild.server.enums.StatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParamsManagerImpl implements ParamsManager {

    @Autowired
    private ConfigParamsDao configParamsDao;

    @Override
    public List<ConfigParams> selectAll() {
        List<ConfigParams> configParamsList = configParamsDao.selectList(null, StatusEnum.NORMAL.toNumbericValue());
        return configParamsList;
    }
}
