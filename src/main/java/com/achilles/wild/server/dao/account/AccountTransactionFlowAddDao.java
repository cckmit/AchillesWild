package com.achilles.wild.server.dao.account;

import com.achilles.wild.server.entity.account.AccountTransactionFlowAdd;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AccountTransactionFlowAddDao {

    int insertSelective(AccountTransactionFlowAdd record);

    String selectFlowNoByKey(@Param("idempotent") String idempotent, @Param("userId") String userId);
}