package com.achilles.wild.server.manager.account.atom.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.achilles.wild.server.dao.account.AccountInterDao;
import com.achilles.wild.server.entity.account.AccountInter;
import com.achilles.wild.server.enums.account.AccountTypeEnum;
import com.achilles.wild.server.manager.account.atom.AccountInterAtomManager;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AccountInterAtomManagerImpl implements AccountInterAtomManager {

    private final static Logger LOG = LoggerFactory.getLogger(AccountInterAtomManagerImpl.class);

    @Resource
    private AccountInterDao accountInterDao;

    @Override
    public AccountInter getPayAccountInter(String userId, Long amount) {

        if(StringUtils.isEmpty(userId)||amount==null||amount<1){
            throw new IllegalArgumentException("userId:"+userId+",amount:"+amount);
        }

        List<AccountInter> accounts = accountInterDao.selectBalanceByLimit(userId, AccountTypeEnum.PAY_ACCOUNT.toNumbericValue(),amount,1);
        if(CollectionUtils.isEmpty(accounts)){
            LOG.info("getPayAccountInter  try again ----------------------");
            accounts = accountInterDao.selectBalanceByLimit(userId, AccountTypeEnum.PAY_ACCOUNT.toNumbericValue(),amount,0);
            if(CollectionUtils.isEmpty(accounts)){
                return null;
            }
        }

        return accounts.get(0);
    }

    @Override
    public AccountInter mergeInterBalance(String userId, Integer accountType, Long amount) {

        if(StringUtils.isEmpty(userId)||amount==null||amount<1){
            throw new IllegalArgumentException("userId:"+userId+",amount:"+amount);
        }

        List<AccountInter> accounts = accountInterDao.selectHasBalance(userId, accountType);
        if(CollectionUtils.isEmpty(accounts)){
            throw new RuntimeException("userId:"+userId+",amount:"+amount+"  mergeInterBalance   get Account null ");
        }

        List<AccountInter> updateAccounts = new ArrayList<>();

        AccountInter account = null;
        Long sum = 0L;
        for(AccountInter act:accounts){
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
            throw new RuntimeException("mergeInterBalance get account null");
        }

        if( amount>sum){
            throw new RuntimeException("mergeInterBalance amount>sum");
        }

        for(AccountInter act:updateAccounts){
            int update = accountInterDao.updateInterBalanceById(act.getId(),act.getBalance());
            if(update==0){
                throw new RuntimeException("mergeInterBalance updateUserBalanceById fail");
            }
        }

        return account;
    }

    @Override
    public boolean reduceInterBalance(Long id, Long amount) {

        if(id==null||id==0||amount==null||amount<=0){
            return false;
        }

        int  update = accountInterDao.reduceInterBalance(id,amount);

        if(update==0){
            return false;
        }

        return true;
    }
}
