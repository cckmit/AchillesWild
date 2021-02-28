package com.achilles.wild.server.business.dao.user;

import com.achilles.wild.server.entity.user.UserToken;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TokenRecordDao {

    int insertSelective(UserToken record);

    UserToken selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserToken record);

    List<UserToken> selectByToken(@Param("token") String token, @Param("status") Integer status);
}