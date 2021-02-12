package com.achilles.wild.server.business.dao.user;

import com.achilles.wild.server.business.entity.user.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {

    int insertSelective(User record);
}