package com.achilles.wild.server.dao.info;

import com.achilles.wild.server.entity.info.CrmOrder;

public interface CrmOrderDao {
    int deleteByPrimaryKey(Long id);

    int insert(CrmOrder record);

    int insertSelective(CrmOrder record);
    
    Long selectIdByMemIdAndNo(CrmOrder record);

    CrmOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CrmOrder record);

    int updateByPrimaryKey(CrmOrder record);
}