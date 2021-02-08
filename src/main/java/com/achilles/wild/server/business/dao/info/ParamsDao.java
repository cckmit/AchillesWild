package com.achilles.wild.server.business.dao.info;

import com.achilles.wild.server.business.entity.info.Params;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ParamsDao {

    int insert(Params params);

    int updateByKey(@Param("key") String key, @Param("val") String val);

    Params selectByKey(String key);
}
