package com.achilles.wild.server.dao.info;

import com.achilles.wild.server.entity.info.Citizen;
import com.achilles.wild.server.model.query.CitizenQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CitizenDao {
    int deleteByPrimaryKey(Long id);

    int insert(Citizen record);

    int insertSelective(Citizen record);
    
    void batchInsert(List<Citizen> list);

    Citizen selectByPrimaryKey(Long id);

    Citizen selectByIdNoAndName(Citizen record);

    Citizen selectByIdNoAndName(CitizenQuery record);

    int updateByPrimaryKeySelective(Citizen record);

    int updateByPrimaryKey(Citizen record);

    List<Citizen> selectList(CitizenQuery query);

    List<Citizen> selectByList(CitizenQuery query);
}