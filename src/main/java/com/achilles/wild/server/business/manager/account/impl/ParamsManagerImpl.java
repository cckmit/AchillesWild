package com.achilles.wild.server.business.manager.account.impl;

import com.achilles.wild.server.business.dao.common.ParamsDao;
import com.achilles.wild.server.entity.common.Params;
import com.achilles.wild.server.business.manager.account.ParamsManager;
import com.achilles.wild.server.enums.StatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParamsManagerImpl implements ParamsManager {

    @Autowired
    private ParamsDao paramsDao;

    @Override
    public List<Params> selectAll() {
        List<Params> paramsList = paramsDao.selectList(null, StatusEnum.NORMAL.toNumbericValue());
        return paramsList;
    }
}
