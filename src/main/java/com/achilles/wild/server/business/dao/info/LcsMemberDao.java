package com.achilles.wild.server.business.dao.info;

import com.achilles.wild.server.entity.info.LcsMember;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LcsMemberDao {
    int deleteByPrimaryKey(Long id);

    int insert(LcsMember record);

    int insertSelective(LcsMember record);

    LcsMember selectByPrimaryKey(Long id);
    
    List<LcsMember> selectList();

    int updateByPrimaryKeySelective(LcsMember record);

    int updateByPrimaryKey(LcsMember record);
}