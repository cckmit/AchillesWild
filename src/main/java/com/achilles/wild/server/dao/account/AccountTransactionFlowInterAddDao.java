package com.achilles.wild.server.dao.account;

import com.achilles.wild.server.entity.account.AccountTransactionFlowInterAdd;

public interface AccountTransactionFlowInterAddDao {

    int insertSelective(AccountTransactionFlowInterAdd record);
}