package com.achilles.wild.server.business.dao.common;

import com.achilles.wild.server.entity.common.ConfigDict;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ConfigDictDao {

    int insertSelective(ConfigDict configDict);

    List<ConfigDict> selectList(@Param("group") String group, @Param("key") String key);
}
