package com.achilles.wild.server.business.dao;

import com.achilles.wild.server.entity.LogFilter;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LogFilterDao {

    int insertSelective(LogFilter record);
}