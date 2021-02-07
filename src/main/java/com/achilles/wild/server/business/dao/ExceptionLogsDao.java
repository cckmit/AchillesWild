package com.achilles.wild.server.business.dao;

import com.achilles.wild.server.business.entity.ExceptionLogs;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ExceptionLogsDao {

    int insertSelective(ExceptionLogs timeLogs);
}
