package com.achilles.wild.server.dao.account;

import com.achilles.wild.server.entity.account.AccountTransactionFlow;
import org.apache.ibatis.annotations.Param;

public interface AccountTransactionFlowDao {


    Integer selectVersionByAccountCode(@Param("accountCode") String accountCode, @Param("userId") String userId, @Param("tradeDay") Integer tradeDay);

    int insertSelective(AccountTransactionFlow record);

    String selectFlowNoByKey(@Param("idempotent") String idempotent, @Param("userId") String userId);
}