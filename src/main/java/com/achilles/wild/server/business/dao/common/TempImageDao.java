package com.achilles.wild.server.business.dao.common;

import com.achilles.wild.server.entity.common.TempImage;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TempImageDao {

    int insertSelective(TempImage record);

    TempImage selectByPrimaryKey(Long id);
}