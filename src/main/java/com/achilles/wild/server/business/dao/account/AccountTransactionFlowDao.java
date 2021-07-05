package com.achilles.wild.server.business.dao.account;

import com.achilles.wild.server.entity.account.AccountTransactionFlow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AccountTransactionFlowDao {

    int insertSelective(AccountTransactionFlow record);

    String selectFlowNoByKey(@Param("idempotent") String idempotent, @Param("userId") String userId);
}