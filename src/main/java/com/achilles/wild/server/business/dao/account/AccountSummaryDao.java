package com.achilles.wild.server.business.dao.account;

import com.achilles.wild.server.entity.account.AccountSummary;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountSummaryDao {

    int deleteByPrimaryKey(Long id);

    int insert(AccountSummary record);

    int insertSelective(AccountSummary record);

    AccountSummary selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AccountSummary record);

    int updateByPrimaryKey(AccountSummary record);
}