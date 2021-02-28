package com.achilles.wild.server.business.dao.common;

import com.achilles.wild.server.entity.common.ConfigParams;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ConfigParamsDao {

    int insert(ConfigParams configParams);

    int updateByKey(@Param("key") String key, @Param("val") String val);

    ConfigParams selectByKey(String key);

    List<ConfigParams> selectList(@Param("key") String key, @Param("status") Integer status);
}
