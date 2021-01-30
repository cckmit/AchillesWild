package com.achilles.wild.server.dao;

import com.achilles.wild.server.entity.Logs;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LogsDao {

    int insertSelective(Logs logs);
}
