package com.achilles.wild.server.business.dao.common;

import com.achilles.wild.server.entity.common.LogBizInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LogBizDao {

    int insertSelective(LogBizInfo logBizInfo);
}
