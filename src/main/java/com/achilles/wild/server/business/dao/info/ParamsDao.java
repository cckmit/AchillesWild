package com.achilles.wild.server.business.dao.info;

import com.achilles.wild.server.business.entity.info.Params;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ParamsDao {

    int insert(Params params);

    int updateByCode(@Param("code") String code, @Param("val") String val);

    Params selectByCode(String code);
}
