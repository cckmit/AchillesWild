package com.achilles.wild.server.business.dao.common;

import com.achilles.wild.server.entity.common.LogTimeInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LogTimeInfoDao {

    int insertSelective(LogTimeInfo logTimeInfo);

    int batchInsert(List<LogTimeInfo> list);
}
