package com.achilles.wild.server.manager.account.atom.impl;

import com.achilles.wild.server.dao.account.AccountDao;
import com.achilles.wild.server.entity.account.Account;
import com.achilles.wild.server.enums.account.AccountTypeEnum;
import com.achilles.wild.server.manager.account.atom.AccountAtomManager;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountAtomManagerImpl implements AccountAtomManager {

    private final static Logger LOG = LoggerFactory.getLogger(AccountAtomManagerImpl.class);

    @Resource
    private AccountDao accountDao;

    @Override
    public Account getPayAccount(String userId,Long amount) {

        if(StringUtils.isEmpty(userId)||amount==null||amount<1){
            throw new IllegalArgumentException("userId:"+userId+",amount:"+amount);
        }

        List<Account> accounts = accountDao.selectBalanceByLimit(userId, AccountTypeEnum.PAY_ACCOUNT.toNumbericValue(),amount,1);
        if(CollectionUtils.isEmpty(accounts)){
            LOG.info("getPayAccount  try again ----------------------");
            accounts = accountDao.selectBalanceByLimit(userId, AccountTypeEnum.PAY_ACCOUNT.toNumbericValue(),amount,0);
            if(CollectionUtils.isEmpty(accounts)){
                return null;
            }
        }

        return accounts.get(0);
    }


    @Override
    public Account mergeBalance(String userId,Integer accountType,Long amount) {


        if(StringUtils.isEmpty(userId)||amount==null||amount<1){
            throw new IllegalArgumentException("userId:"+userId+",amount:"+amount);
        }

        List<Account> accounts = accountDao.selectHasBalance(userId, accountType);
        if(CollectionUtils.isEmpty(accounts)){
            throw new RuntimeException("userId:"+userId+",amount:"+amount+"  mergeBalance   get Account null ");
        }

        List<Account> updateAccounts = new ArrayList<>();

        Account account = null;
        Long sum = 0L;
        for(Account act:accounts){
            sum += act.getBalance();
            act.setBalance(0L);
            updateAccounts.add(act);
            if(sum >= amount){
                act.setBalance(sum);
                account = act;
                break;
            }
        }

        if(account==null ){
            throw new RuntimeException("mergeBalance get account null");
        }


        if( amount>sum){
            throw new RuntimeException("mergeBalance amount>sum");
        }

        for(Account act:updateAccounts){
            int update = accountDao.updateUserBalanceById(act.getId(),act.getBalance());
            if(update==0){
                throw new RuntimeException("mergeBalance updateUserBalanceById fail");
            }
        }

        return account;
    }

    @Override
    public boolean reduceBalance(Long id, Long amount) {

        if(id==null||id==0||amount==null||amount<=0){
            return false;
        }

        int  update = accountDao.reduceUserBalance(id,amount);

        if(update==0){
            return false;
        }

        return true;
    }
}
