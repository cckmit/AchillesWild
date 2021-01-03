package com.achilles.wild.server.dao.account;

import com.achilles.wild.server.entity.account.AccountSummary;

public interface AccountSummaryDao {

    int deleteByPrimaryKey(Long id);

    int insert(AccountSummary record);

    int insertSelective(AccountSummary record);

    AccountSummary selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AccountSummary record);

    int updateByPrimaryKey(AccountSummary record);
}