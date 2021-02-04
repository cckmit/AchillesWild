package com.achilles.wild.server.business.manager.account;

import com.achilles.wild.server.business.entity.account.AccountInter;
import org.apache.ibatis.annotations.Param;

/**
 * inter account
 */
public interface AccountInterManager {


    boolean updateBalanceById(Long id, Long amount);

    boolean updateInterBalanceById(Long id, Long balance);

    AccountInter getCollectAccountInter();

    Long getBalance(String userId);

    boolean reduceBalance(Long id, Long amount);

    AccountInter getAccountById(Long id);

    long getVersionById(@Param("id") Long id);


    AccountInter reduceInterBalance(Long amount);
}
