package com.achilles.wild.server.business.dao.user;

import com.achilles.wild.server.entity.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserDao {

    int insertSelective(User record);

    List<User> selectUserByEmail(@Param("email") String email, @Param("status") Integer status);
}