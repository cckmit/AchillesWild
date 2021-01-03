package com.achilles.wild.server.dao.account;

import java.util.List;

import com.achilles.wild.server.entity.account.AccountInter;
import org.apache.ibatis.annotations.Param;

public interface AccountInterDao {

    int insertSelective(AccountInter record);

    List<AccountInter> selectAccountByType(Integer accountType);

    int updateBalanceById(@Param("id") Long id, @Param("amount") Long amount);

    int updateInterBalanceById(@Param("id") Long id, @Param("balance") Long balance);

    Long selectBalance(@Param("userId") String userId);

    List<AccountInter> selectBalanceByLimit(@Param("userId") String userId,@Param("accountType") Integer accountType,@Param("amount") Long amount,@Param("skipLocked") Integer skipLocked);

    List<AccountInter> selectHasBalance(@Param("userId") String userId,@Param("accountType") Integer accountType);

    int reduceInterBalance(@Param("id") Long id,@Param("amount") Long amount);

    AccountInter selectById(@Param("id") Long id);

    long selectVersionById(@Param("id") Long id);
}