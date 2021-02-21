package com.achilles.wild.server.business.dao.account;

import com.achilles.wild.server.entity.account.Account;
import com.achilles.wild.server.model.query.account.AccountQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AccountDao {

    int insertSelective(Account record);

    void batchInsert(List<Account> list);

    long selectCount(AccountQuery query);

    int updateBalanceById(@Param("id") Long id,@Param("amount") Long amount);

    int updateUserBalanceById(@Param("id") Long id,@Param("balance") Long balance);

    Account selectAccountByCode(@Param("accountCode") String accountCode, @Param("userId") String userId);

    List<Account> selectBalanceByCodes(@Param("accountCodes")List<String> accountCodes,@Param("userId") String userId);

    Long selectUserBalance(@Param("userId") String userId);

    List<Account> selectBalanceByLimit(@Param("userId") String userId,@Param("accountType") Integer accountType,@Param("amount") Long amount,@Param("skipLocked") Integer skipLocked);

    List<Account> selectHasBalance(@Param("userId") String userId,@Param("accountType") Integer accountType);

    int reduceUserBalance(@Param("id") Long id,@Param("amount") Long amount);

    Account selectById(@Param("id") Long id);

    long selectVersionById(@Param("id") Long id);


}