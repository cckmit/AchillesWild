package com.achilles.wild.server.business.dao;

import com.achilles.wild.server.entity.common.LogController;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LogControllerDao {

    int insertSelective(LogController controllerLogs);
}
