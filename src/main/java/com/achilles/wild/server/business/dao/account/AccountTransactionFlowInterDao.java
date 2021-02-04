package com.achilles.wild.server.business.dao.account;

import com.achilles.wild.server.business.entity.account.AccountTransactionFlowInter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AccountTransactionFlowInterDao {

    Integer selectVersionByAccountCode(@Param("accountCode") String accountCode, @Param("userId") String userId,
        @Param("tradeDay") Integer tradeDay);

    int insertSelective(AccountTransactionFlowInter record);


    String selectFlowNoByKey(@Param("idempotent") String idempotent, @Param("userId") String userId);

}