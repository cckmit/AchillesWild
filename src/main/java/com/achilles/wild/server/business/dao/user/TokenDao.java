package com.achilles.wild.server.business.dao.user;

import com.achilles.wild.server.business.entity.user.Token;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TokenDao {

    int insertSelective(Token record);

    Token selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Token record);
}