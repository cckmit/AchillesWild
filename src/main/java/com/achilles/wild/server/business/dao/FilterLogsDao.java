package com.achilles.wild.server.business.dao;

import com.achilles.wild.server.entity.FilterLogs;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FilterLogsDao {

    int insertSelective(FilterLogs record);
}