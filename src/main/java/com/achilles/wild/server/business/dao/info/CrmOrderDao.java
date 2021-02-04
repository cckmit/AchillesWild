package com.achilles.wild.server.business.dao.info;

import com.achilles.wild.server.business.entity.info.CrmOrder;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CrmOrderDao {
    int deleteByPrimaryKey(Long id);

    int insert(CrmOrder record);

    int insertSelective(CrmOrder record);
    
    Long selectIdByMemIdAndNo(CrmOrder record);

    CrmOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CrmOrder record);

    int updateByPrimaryKey(CrmOrder record);
}