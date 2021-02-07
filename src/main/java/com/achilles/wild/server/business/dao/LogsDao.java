package com.achilles.wild.server.business.dao;

import com.achilles.wild.server.business.entity.TimeLogs;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LogsDao {

    int insertSelective(TimeLogs timeLogs);
}
