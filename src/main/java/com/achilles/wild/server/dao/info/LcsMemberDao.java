package com.achilles.wild.server.dao.info;

import java.util.List;

import com.achilles.wild.server.entity.info.LcsMember;

public interface LcsMemberDao {
    int deleteByPrimaryKey(Long id);

    int insert(LcsMember record);

    int insertSelective(LcsMember record);

    LcsMember selectByPrimaryKey(Long id);
    
    List<LcsMember> selectList();

    int updateByPrimaryKeySelective(LcsMember record);

    int updateByPrimaryKey(LcsMember record);
}