package com.achilles.wild.server.business.dao.common;

import com.achilles.wild.server.entity.common.LogBizInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LogBizDao {

    int insertSelective(LogBizInfo logBizInfo);

    int batchInsert(List<LogBizInfo> list);
}
