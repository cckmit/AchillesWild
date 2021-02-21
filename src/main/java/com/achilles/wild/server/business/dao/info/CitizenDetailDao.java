package com.achilles.wild.server.business.dao.info;

import com.achilles.wild.server.entity.info.CitizenDetail;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CitizenDetailDao {
    int deleteByPrimaryKey(Long id);

    int insert(CitizenDetail record);

    int insertSelective(CitizenDetail record);

    CitizenDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CitizenDetail record);
    
    int updateByUuidSelective(CitizenDetail record);

    int updateByPrimaryKey(CitizenDetail record);
}