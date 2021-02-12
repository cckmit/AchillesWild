package com.achilles.wild.server.business.dao.user;

import com.achilles.wild.server.business.entity.user.TokenRecord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TokenRecordDao {

    int insertSelective(TokenRecord record);

    TokenRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TokenRecord record);
}