package com.achilles.wild.server.dao.info;

import java.util.List;

import com.achilles.wild.server.entity.info.CrmClient;

public interface CrmClientDao {
    int deleteByPrimaryKey(Long idd);

    int insert(CrmClient record);

    int insertSelective(CrmClient record);

    CrmClient selectByPrimaryKey(Long idd);
    
    CrmClient selectByIdNoAndName(CrmClient record);
    
    List<CrmClient> selectList(); 

    int updateByPrimaryKeySelective(CrmClient record);

    int updateByPrimaryKey(CrmClient record);
}