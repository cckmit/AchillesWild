package com.achilles.wild.server.business.dao;

import com.achilles.wild.server.entity.common.LogFilterInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LogFilterInfoDao {

    int insertSelective(LogFilterInfo record);
}