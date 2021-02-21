package com.achilles.wild.server.business.dao.info;

import com.achilles.wild.server.entity.info.CrmClient;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
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