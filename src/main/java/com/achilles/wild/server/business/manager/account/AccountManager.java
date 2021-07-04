package com.achilles.wild.server.business.manager.account;

import com.achilles.wild.server.entity.account.Account;
import com.achilles.wild.server.model.query.account.AccountQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * account
 */
public interface AccountManager {

    boolean addAccount(Account account);

    boolean ifExist(AccountQuery query);

    boolean addAccounts(List<Account> list);

    String getAccountCode(Integer accountType);

    /**
     * 查单个账户余额
     * 
     * @param accountCode
     * @return
     */
    Account getBalanceByCode(String accountCode,String userId);

    boolean updateBalanceById(Long id,Long amount);

    boolean updateUserBalanceById(Long id,Long balance);

    Long getUserBalance(String userId);

    Long getUserBalanceById(Long id);

    Account getUserAccount(String userId);

    Account getCollectAccount(String userId);


    Account getAccountById(Long id);


    long getVersionById(@Param("id") Long id);


    boolean reduceUserBalance(Account account,Long amount);

}
